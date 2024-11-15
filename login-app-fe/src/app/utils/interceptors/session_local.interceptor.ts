import { HttpInterceptor, HttpEvent, HttpHandler, HttpRequest, HttpEventType } from "@angular/common/http";
import { Observable } from "rxjs";
import { tap } from 'rxjs/operators';
import { Injectable, NgZone } from "@angular/core";
import { environment } from "src/environments/environment";
import { RestDataSource } from "src/app/model/rest.datasource";
import { AuthService } from "src/app/model/services/auth.service";

@Injectable()
export class SessionLocalInterceptor implements HttpInterceptor {
   
    constructor(public auth: AuthService, private zone: NgZone, 
                    private restDataSource: RestDataSource) {

    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<any> {
        return next.handle(request).pipe(
            tap(
                (event: HttpEvent<any>) => {
                    if (event!=null && event.type==HttpEventType.Sent) {
                        if(this.isCurrentSessionTabExpired()){
                            this.copyLocalTokenToSession();
                            this.reloadPage();
                            return;
                        }
                    }
                },
                (error: any) => {
                    if(this.isCurrentSessionTabExpired()){
                        this.copyLocalTokenToSession();
                        this.reloadPage();
                        return;
                    }
                }
            )
        );
    }

    isCurrentSessionTabExpired(): boolean{
        //made this double checks for compatibility with edge browser
        if(sessionStorage.getItem(environment.AUTH_TOKEN_KEY)==null || sessionStorage.getItem(environment.AUTH_TOKEN_KEY)=='null'){
            this.copyLocalTokenToSession();
        }
        let areDiff: boolean= sessionStorage.getItem(environment.AUTH_TOKEN_KEY) != localStorage.getItem(environment.AUTH_TOKEN_KEY);
        
        return areDiff;
    }
   
    copyLocalTokenToSession(): void{
        if(localStorage.getItem(environment.AUTH_TOKEN_KEY)==null 
                        || localStorage.getItem(environment.AUTH_TOKEN_KEY)=='null'){
            sessionStorage.setItem(environment.AUTH_TOKEN_KEY, null); //made this double checks for compatibility with edge
            localStorage.setItem(environment.AUTH_TOKEN_KEY, null);
        }else{
            sessionStorage.setItem(environment.AUTH_TOKEN_KEY, localStorage.getItem(environment.AUTH_TOKEN_KEY));
            this.restDataSource.saveUsernameAndRole(localStorage.getItem(environment.AUTH_TOKEN_KEY));
        }
        
    }

    reloadPage() { // click handler or similar
        this.zone.runOutsideAngular(() => {
            location.reload();
        });
    }


}