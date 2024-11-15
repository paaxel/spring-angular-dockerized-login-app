import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SetLanguageService } from './language/set-language.service';
import { TranslationLoaderResolver } from './language/translation-loader.resolver';
import { NotificationsModule } from '../dialogs/notifications/notifications.module';
import { SessionLocalInterceptor } from './interceptors/session_local.interceptor';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { UnauthorizedInterceptor } from './interceptors/unauthorized.interceptor';
import { ServiceUnavailableInterceptor } from './interceptors/service-unavailable.interceptor';
import { MessageNotifierService } from '../dialogs/notifications/message-notifier.service';
import { ServiceUnavailableService } from '../dashboard/components/service-unavailable/service-unavailable.service';
import { DateFormatPipe } from './pipes/date-format.pipe';
import { TranslateModule } from '@ngx-translate/core';
import { ChainExceptionHandler } from './exceptions/chain-exception-handler.service';
import { BackgroundThemingService } from './theming/background-theming.service';

@NgModule({
  imports: [
      CommonModule, NotificationsModule, TranslateModule
  ],
  declarations: [
    DateFormatPipe
  ],

  exports: [
     DateFormatPipe
  ],

  

  providers: [ 
      TranslationLoaderResolver,
      SetLanguageService,
      ChainExceptionHandler,
      BackgroundThemingService,

      SessionLocalInterceptor, UnauthorizedInterceptor, ServiceUnavailableInterceptor,

      { provide: HTTP_INTERCEPTORS, useClass: SessionLocalInterceptor, multi: true },
      { provide: HTTP_INTERCEPTORS, useClass: UnauthorizedInterceptor, multi: true },
      
      { provide: HTTP_INTERCEPTORS, useClass: ServiceUnavailableInterceptor, multi: true,
        deps: [MessageNotifierService,  ServiceUnavailableService]
        },

      
    ]
})
export class UtilsModule { }
