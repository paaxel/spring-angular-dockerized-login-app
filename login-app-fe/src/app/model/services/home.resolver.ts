import { Injectable } from '@angular/core';
import { HomeService } from './home.service';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { LoadingService } from '../../dialogs/loading/loading.service';
import { ResolvableService } from './resolvable-service.model';
import { AbstractResolver } from './abstract.resolver';

@Injectable()
export class HomeResolver extends AbstractResolver {

    constructor(private homeService: HomeService, private loader: LoadingService){
        super();
    }

    getLoadingService(): LoadingService {
        return this.loader;
    }

    getService(): ResolvableService {
        return this.homeService;
    }

    override resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean>{
        this.homeService.reset();

        return super.resolve(route, state);
    }

}