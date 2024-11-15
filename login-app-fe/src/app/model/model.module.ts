import { NgModule } from "@angular/core";
import { BackendUrlsService } from "./backend-urls.service";
import { RestDataSource } from "./rest.datasource";
import { AuthService } from "./services/auth.service";
import { HomeResolver } from "./services/home.resolver";
import { HomeService } from "./services/home.service";


@NgModule({
    imports : [
        
    ],
    providers: [ 
                 RestDataSource, AuthService, BackendUrlsService,
                 HomeService, HomeResolver
                ]
})
export class ModelModule { }