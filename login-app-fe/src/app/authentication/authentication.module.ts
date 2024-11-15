import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { FormsModule } from "@angular/forms";
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from "@angular/router";
import { TranslateModule } from "@ngx-translate/core";

import { LoginComponent } from "./login/login.component";


import { IsLoggedInGuard } from "./guards/isLoggedIn.guard";
import { ModelModule } from '../model/model.module';
import { ComponentsModule } from "../dashboard/components/components.module";
import { TranslationLoaderResolver } from "../utils/language/translation-loader.resolver";

let routing = RouterModule.forChild([
    {path: "login", component: LoginComponent, resolve: {model: TranslationLoaderResolver}}
])


@NgModule({
    imports: [
        BrowserModule, 
        FormsModule, 
        TranslateModule, 
        ReactiveFormsModule, 
        routing,
        ModelModule,
        ComponentsModule
    ],
    declarations: [
        LoginComponent
    ],
    exports: [],
    providers: [
        IsLoggedInGuard
    ]

})
export class AuthenticationModule { }