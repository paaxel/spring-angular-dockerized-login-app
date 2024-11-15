import { Injectable } from "@angular/core";
import { MessageNotifierService } from "../../dialogs/notifications/message-notifier.service";


@Injectable()
export class ChainExceptionHandler {
    
    public static SERVER_UNREACHABLE: number = 0;
    public static BAD_DATA = 400;
    public static UNAUTHORIZED: number = 401;   
    public static FORBIDDEN: number = 403;
    public static NOT_FOUND: number = 404;
    public static INTERNAL_SERVER_ERROR: number= 500; 
    public static Service_Unavailable:number = 503;

    public static MANAGED_STATUS : number[] = [ChainExceptionHandler.SERVER_UNREACHABLE, ChainExceptionHandler.BAD_DATA, 
            ChainExceptionHandler.UNAUTHORIZED, ChainExceptionHandler.FORBIDDEN, ChainExceptionHandler.NOT_FOUND,
            ChainExceptionHandler.INTERNAL_SERVER_ERROR];

  static f: number;

    constructor(private notifier: MessageNotifierService){
    }

    
    /**
     * Managed errors 0, 500, 401, 403 
     * @param nextChainHandler function to be called if the error can't be managed
     */
    manageError(status: number) {
        if(status==ChainExceptionHandler.SERVER_UNREACHABLE){
            this.notifier.notifyErrorWithI18nAndStandardTitle("message.unreachable-server");
            return;
        }
        if(status>=ChainExceptionHandler.INTERNAL_SERVER_ERROR){
            this.notifier.notifyErrorWithI18nAndStandardTitle("message.internal-server-error");
            return;
        }
        if(status==ChainExceptionHandler.FORBIDDEN){
            this.notifier.notifyErrorWithI18nAndStandardTitle("message.forbidden");
            return;
        }
        if(status==ChainExceptionHandler.UNAUTHORIZED){
            this.notifier.notifyErrorWithI18nAndStandardTitle("message.unauthorized");
            return;
        }
        if(status==ChainExceptionHandler.NOT_FOUND){
            this.notifier.notifyErrorWithI18nAndStandardTitle("message.not-found");
            return;
        }
        if(status==ChainExceptionHandler.BAD_DATA){
            this.notifier.notifyErrorWithI18nAndStandardTitle("message.bad-data");
            return;
        }

        this.notifier.notifyErrorWithI18nAndStandardTitle("message.generic-error");
    }
        

}