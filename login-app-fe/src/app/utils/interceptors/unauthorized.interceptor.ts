import { Injectable, NgZone } from "@angular/core";
import { HttpInterceptor, HttpRequest, HttpHandler, HttpResponse, HttpEvent, HttpErrorResponse } from "@angular/common/http";
import { tap } from "rxjs/operators";
import { Observable } from "rxjs";
import { Router } from "@angular/router";
import { AuthService } from "src/app/model/services/auth.service";
import { ChainExceptionHandler } from "../exceptions/chain-exception-handler.service";

declare const $: any;

@Injectable()
export class UnauthorizedInterceptor implements HttpInterceptor {

    currentlyCheckNumber : number = 0;

    constructor(private router: Router,  private auth: AuthService){

    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<any> {
        return next.handle(request).pipe(
            tap(
                (event) => {
                    if (event instanceof HttpResponse) {
                        if(event.status === ChainExceptionHandler.UNAUTHORIZED){
                            this.manageUnauthorizedAccess();
                        }
                    }
                },
                (error: any) => {
                    if (error instanceof HttpErrorResponse) {
                        if (error.status === ChainExceptionHandler.UNAUTHORIZED) {
                            this.manageUnauthorizedAccess();
                        }
                    }
                }
            )
        );
    }


    manageUnauthorizedAccess(){
        this.executeLogout();
    }

    executeLogout(){
        this.auth.logout();
        this.router.navigateByUrl("/login");
        $('.modal').modal('hide'); //hide all modals that could be opened.
    }
}