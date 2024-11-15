import { Injectable, NgZone } from "@angular/core";
import { HttpRequest, HttpClient, HttpHeaders, HttpEvent, HttpParams, HttpResponse, HttpBackend } from '@angular/common/http';
import { Observable } from "rxjs";
import { environment } from "../../environments/environment";
import { JwtHelper } from "../utils/jwt-helper.model";


const TOKEN_START: string = "Bearer ";
const AUTHORIZATION_HEADER : string = "Authorization";

const USERNAME_KEY: string = 'username';


/**
 * @author Alessandro Pagliaro
 * 
 */
@Injectable()
export class RestDataSource {
    
    
    jwtHelper: JwtHelper;
    notInterceptedHttpClient: HttpClient;

    constructor(private httpClient: HttpClient, handler: HttpBackend,
                    private zone: NgZone) {
        this.jwtHelper = new JwtHelper();
        this.notInterceptedHttpClient = new HttpClient(handler);
    }

    saveAuthToken(token: string): void{
        localStorage.setItem(environment.AUTH_TOKEN_KEY, token);
        sessionStorage.setItem(environment.AUTH_TOKEN_KEY, token);

        this.saveUsernameAndRole(token);
    }

    saveUsernameUpdated(username){
        localStorage.setItem(USERNAME_KEY, username);

        sessionStorage.setItem(USERNAME_KEY, username);
    }

    saveUsernameAndRole(token: string){
        let tokenContent: any = this.jwtHelper.decodeToken(token);

        localStorage.setItem(USERNAME_KEY, tokenContent.sub);

        sessionStorage.setItem(USERNAME_KEY, tokenContent.sub);
    }

    get username() {
        return localStorage.getItem(USERNAME_KEY);
    }
      
    getAuthToken(): string{
        return localStorage.getItem(environment.AUTH_TOKEN_KEY);
    }

    deleteAuthToken(): void{
        localStorage.removeItem(environment.AUTH_TOKEN_KEY);
        sessionStorage.removeItem(environment.AUTH_TOKEN_KEY);

        localStorage.removeItem(USERNAME_KEY);
        sessionStorage.removeItem(USERNAME_KEY);
    }

    isCurrentTokenExpired(): boolean{
        if( this.getAuthToken()==null || this.getAuthToken()=="null" ){
            return true;
        }
        try{
            if(this.jwtHelper.isTokenExpired(this.getAuthToken())){
                return true;
            }
          }catch(error){
            return true;
        }
        return false;
    }


    reloadPage() { // click handler or similar
        this.zone.runOutsideAngular(() => {
            location.reload();
        });
    }

    public makePostJsonObject<T>(url: string, form: any, params: HttpParams = new HttpParams(), headers: HttpHeaders = new HttpHeaders(), 
                            authenticated: boolean = true, withCredentials: boolean = false, disableInterceptor: boolean = false): Observable<T> {
        let header: HttpHeaders = headers;
        header = header.append('Content-Type', 'application/json');
        if(authenticated){
            header = header.append(AUTHORIZATION_HEADER, TOKEN_START+this.getAuthToken()+"");
        }

        let httpClientSelected = this.httpClient;
        if(disableInterceptor){
            httpClientSelected = this.notInterceptedHttpClient;
        }

        return httpClientSelected.post<T>(url, form, { headers: header, params: params, withCredentials: withCredentials } );
    }

    public makePostSOAPObject(url: string, body: any, params: HttpParams = new HttpParams(), headers: HttpHeaders = new HttpHeaders(),
                                authenticated: boolean = true, disableInterceptor: boolean = false) {
        
        let header: HttpHeaders = headers;
        header = header.append('Content-Type', 'text/xml')
        
        if(authenticated){
            header = header.append(AUTHORIZATION_HEADER, TOKEN_START+this.getAuthToken()+"");
        }

        let httpClientSelected = this.httpClient;
        if(disableInterceptor){
            httpClientSelected = this.notInterceptedHttpClient;
        }
        return httpClientSelected.post(url, body, { headers: header, params: params, responseType: 'text' } );                           
    }

  

}