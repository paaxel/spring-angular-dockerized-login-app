import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { FormsModule } from '@angular/forms';

import { NavbarComponent } from './navbar/navbar.component';
import { ServiceUnavailableComponent } from './service-unavailable/service-unavailable.component';
import { ServiceUnavailableService } from './service-unavailable/service-unavailable.service';
import { FooterComponent } from './footer/footer.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    TranslateModule,
    FormsModule
  ],
  declarations: [
    NavbarComponent,
    ServiceUnavailableComponent,
    FooterComponent
  ],
  exports: [
    NavbarComponent,
    ServiceUnavailableComponent,
    FooterComponent
  ],
  providers: [
    ServiceUnavailableService
  ]
})
export class ComponentsModule { }
