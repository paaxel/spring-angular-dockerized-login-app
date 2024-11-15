import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { TranslateModule } from '@ngx-translate/core';

import { ComponentsModule } from "./components/components.module";
import { HomeComponent } from './home/home.component';
import { DashboardRoutingModule } from "./dashboard.routing";

import { UtilsModule } from '../utils/utils.module';
import { DashboardComponent } from './dashboard.component';

@NgModule({
  imports: [ 
              CommonModule, FormsModule, ComponentsModule, 
              DashboardRoutingModule, RouterModule, TranslateModule,
              UtilsModule
            ],
  providers: [
    
  ],
  declarations: [
                  DashboardComponent, HomeComponent
                ],
  bootstrap: [
                  DashboardComponent
             ]
})
export class DashboardModule { }


