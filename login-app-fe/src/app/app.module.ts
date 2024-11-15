import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { environment } from 'src/environments/environment';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ComponentsModule } from './dashboard/components/components.module';
import { LoadingModule } from './dialogs/loading/loading.module';
import { NotificationsModule } from './dialogs/notifications/notifications.module';
import { UtilsModule } from './utils/utils.module';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { AuthenticationModule } from './authentication/authentication.module';
import { DashboardModule } from './dashboard/dashboard.module';
import { RouterModule } from '@angular/router';


export function createTranslateLoader(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json?'+environment.frontend_version);
}

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    
    NotificationsModule,
    RouterModule, 
    LoadingModule,
    ComponentsModule,
    UtilsModule,
    HttpClientModule,
    DashboardModule,
    AuthenticationModule,
    AppRoutingModule,

    TranslateModule.forRoot({
      loader: {
                  provide: TranslateLoader,
                  useFactory: (createTranslateLoader),
                  deps: [HttpClient]
              }
    })

    
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
