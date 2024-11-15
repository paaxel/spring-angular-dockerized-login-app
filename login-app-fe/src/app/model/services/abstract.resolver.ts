import { ActivatedRouteSnapshot, RouterStateSnapshot } from "@angular/router";
import { Observable } from "rxjs";
import { of } from 'rxjs';
import { LoadingService } from "../../dialogs/loading/loading.service";
import { HttpErrorResponse } from "@angular/common/http";
import { ResolvableService } from "./resolvable-service.model";

export abstract class AbstractResolver {

    abstract getService(): ResolvableService;
    abstract getLoadingService(): LoadingService;

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean>{
        if(!this.getService().areDataLoaded()){
            this.getLoadingService().startLoading();
            
            return Observable.create(
                (observer) => {
                    let observable: Observable<any>  = this.getService().loadInitialInformation(route.params);
                    if(observable==null){
                        observer.next(true);
                        observer.complete(); 
                        return;
                    }
                    observable.subscribe(
                        (successful) => {
                            if(successful){
                                observer.next(true);
                                observer.complete(); 
                            }else{
                                this.getLoadingService().endLoading()
                                observer.next(false);
                            }
                        },
                        (error: HttpErrorResponse) =>{
                            this.getService().onResolveFailure(error);
                            observer.next(false);
                            throw error;
                        }
                    );
                }
            );
        }
        return of(true);
    }
    

    
}