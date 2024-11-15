import { NgModule } from '@angular/core';
import { CommonModule, } from '@angular/common';
import { BrowserModule  } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { DashboardComponent } from "./dashboard.component";
import { HomeComponent } from './home/home.component';
import { TranslationLoaderResolver } from '../utils/language/translation-loader.resolver';
import { IsLoggedInGuard } from '../authentication/guards/isLoggedIn.guard';
import { HomeResolver } from '../model/services/home.resolver';



const routing = RouterModule.forChild([
    {path: "dashboard", component: DashboardComponent, resolve: {model: TranslationLoaderResolver}, 

     canActivate: [IsLoggedInGuard], canActivateChild: [IsLoggedInGuard],
     children: [

        {path:'home', component: HomeComponent, pathMatch: 'full',
                resolve: {model: HomeResolver},
        },
        {path: "**", redirectTo:'home'}
     ] 
    }
])


@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    routing
  ],
  exports: [
  ],
})
export class DashboardRoutingModule { }