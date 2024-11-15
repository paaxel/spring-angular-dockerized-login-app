import { Injectable } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Params } from '@angular/router';
import { Observable, of, forkJoin, throwError, map, catchError } from 'rxjs';
import { RestDataSource } from '../rest.datasource';
import { BackendUrlsService } from '../backend-urls.service';
import { ResolvableService } from './resolvable-service.model';
import { UserProfileDTO } from '../dto/user-profile.model';
import { ChainExceptionHandler } from '../../utils/exceptions/chain-exception-handler.service';
import { AuthService } from './auth.service';

@Injectable()
export class HomeService implements ResolvableService {

    private dataAreLoaded: boolean = false;
    userProfile: UserProfileDTO;

    constructor(private datasource: RestDataSource, private backendUrlsSrv: BackendUrlsService,
                    private exceptionHandler: ChainExceptionHandler, private authService: AuthService) {
    }

    getService(): ResolvableService {
        return this;
    }

    reset(): void {
        this.dataAreLoaded = false;
        this.userProfile = null
    }

    areDataLoaded(): boolean {
        return this.dataAreLoaded;
    }

    onResolveFailure(error: HttpErrorResponse): void {
    }

    loadInitialInformation(routeParams?: Params): Observable<any> {
        if (this.dataAreLoaded) {
            return of(true);
        }

        let functionToCall = forkJoin(
            this.loadUserProfile()
        )

        return Observable.create(
            (observer) => {

                functionToCall.subscribe(
                    (successful) => {
                        this.dataAreLoaded = true;
                        observer.next(true);
                        observer.complete();
                    }, error => {
                        this.dataAreLoaded = false;
                        this.exceptionHandler.manageError(error.status);
                        observer.next(true);
                        observer.complete();
                    }
                );
            }
        );
    }

    loadUserProfile() {
        // The following variable contains the xml SOAP request.
        const body =
            `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:soap="http://provaLogin.palex.it/web/soap">
               <soapenv:Header/>
                 <soapenv:Body>
                    <soap:getProfileRequest>
             
                    </soap:getProfileRequest>
                 </soapenv:Body>
               </soapenv:Envelope>`;

        return this.datasource.makePostSOAPObject(this.backendUrlsSrv.getProfileWsUrl(), body, new HttpParams(),
            new HttpHeaders(), true, false)
            .pipe(
                map(
                    (succ) => {
                        let responseJson = this.xmlStringToJson(succ);
                        this.userProfile = this.extractProfile(responseJson)

                        return true;
                    }
                ),
                catchError(
                    (err) => {
                        this.manageError(err)

                        return of(false);
                    })
            )

    }

    private manageError(httpError){
        let statusCode = this.extractStatusCodeFromSoapErrorResponse(httpError);

        if(statusCode==ChainExceptionHandler.UNAUTHORIZED){
            this.exceptionHandler.manageError(statusCode);
            //execute logout if the error is a 401
            this.authService.logout()
            this.authService.redirectToLogin()
            return;
        }

        //show error message
        this.exceptionHandler.manageError(statusCode);
    }

    private extractStatusCodeFromSoapErrorResponse(httpError){
        let statusCode = 500;
        let errorBodyInJson = this.xmlStringToJson(httpError.error)

        let envelop = errorBodyInJson['SOAP-ENV:Envelope']
        if(envelop!=null){
            let soapBody = envelop['SOAP-ENV:Body']

            if(soapBody!=null){
                let fault = soapBody['SOAP-ENV:Fault']
                if(fault!=null){
                    let errorDetail = fault['detail']
                    if(errorDetail!=null){
                        let errorCode = errorDetail['statusCode']
                        if(errorCode!=null && errorCode['#text']!=null){
                            return errorCode['#text']
                        }
                    }
                }
                
            }
            
        }

        return statusCode;
    }

    private extractProfile(responseJson){
        let res: UserProfileDTO = new UserProfileDTO();
        
        let envelop = responseJson['SOAP-ENV:Envelope']
        if(envelop!=null){
            let soapBody = envelop['SOAP-ENV:Body']

            if(soapBody!=null){
                let profileResponse = soapBody['ns2:getProfileResponse']

                if(profileResponse!=null){
                    let profile = profileResponse['ns2:profile']

                    if(profile!=null){
                        res.id = profile['ns2:id']['#text']!=null ? profile['ns2:id']['#text']:null
                        res.name = profile['ns2:name']['#text']!=null ? profile['ns2:name']['#text']:null
                        res.surname = profile['ns2:surname']['#text']!=null ? profile['ns2:surname']['#text']:null
                        res.registrationDate = profile['ns2:registrationDate']!=null ? profile['ns2:registrationDate']['#text']:null
                    }
                }

            }

        }
        return res;
    }


    xmlStringToJson(xml: string) {
        // Convert the XML string to an XML Document.
        const oParser = new DOMParser();
        const oDOM = oParser.parseFromString(xml, "application/xml");
        // Convert the XML Document to a JSON Object.
        return this.xmlToJson(oDOM);
    }

    xmlToJson(xml) {
        // Create the return object
        var obj = {};

        if (xml.nodeType == 1) { // element
            // do attributes
            if (xml.attributes.length > 0) {
                obj["@attributes"] = {};
                for (var j = 0; j < xml.attributes.length; j++) {
                    var attribute = xml.attributes.item(j);
                    obj["@attributes"][attribute.nodeName] = attribute.nodeValue;
                }
            }
        } else if (xml.nodeType == 3) { // text
            obj = xml.nodeValue;
        }

        // do children
        if (xml.hasChildNodes()) {
            for (var i = 0; i < xml.childNodes.length; i++) {
                var item = xml.childNodes.item(i);
                var nodeName = item.nodeName;
                if (typeof (obj[nodeName]) == "undefined") {
                    obj[nodeName] = this.xmlToJson(item);
                } else {
                    if (typeof (obj[nodeName].push) == "undefined") {
                        var old = obj[nodeName];
                        obj[nodeName] = [];
                        obj[nodeName].push(old);
                    }
                    obj[nodeName].push(this.xmlToJson(item));
                }
            }
        }
        return obj;
    }


}