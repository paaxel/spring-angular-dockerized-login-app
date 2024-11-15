import { Component, OnInit } from '@angular/core';
import { NgForm } from "@angular/forms";
import { Router } from "@angular/router";
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ChainExceptionHandler } from 'src/app/utils/exceptions/chain-exception-handler.service';
import { BackgroundThemingService } from 'src/app/utils/theming/background-theming.service';
import { AuthService } from 'src/app/model/services/auth.service';
import { GenericResponse } from 'src/app/model/dto/generic-response.model';
import { SuccessfulAuthenticationDTO } from 'src/app/model/dto/successfull-authentication.model';


@Component({
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  
  username: string;
  password: string;
  private loginError : boolean = false;
  private connectionError : boolean = false;

  areCurrentRequestingAuthentication: boolean = false;

  constructor(private router: Router,private auth: AuthService, private themingSrv: BackgroundThemingService,
                  private exceptionHandler: ChainExceptionHandler) { }

  ngOnInit() {
    this.themingSrv.setStandardColouredBackground();
    this.loginError = false;
    this.areCurrentRequestingAuthentication = false;
    this.hideAllErrors();
  }


  showLoginError() : boolean{
    return this.loginError;
  }

  showConnectionError() : boolean{
    return this.connectionError;
  }


  authenticate(form: NgForm){
    if(form.valid){
      this.executeAuthentication();
    }else{
      this.showOnlyLoginError();
    }
  }


  private executeAuthentication(){
        if(this.areCurrentRequestingAuthentication){
          return;
        }
        this.hideAllErrors();
        this.areCurrentRequestingAuthentication = true;
        this.auth.authenticate(this.username, this.password)
        .subscribe(
                (res: GenericResponse<SuccessfulAuthenticationDTO>)  => {
                  this.manageSuccessfullLogin(res);
                  this.areCurrentRequestingAuthentication = false;
                  this.password = null; //clear password from memory
                },
                (error: HttpErrorResponse) =>{
                  this.manageLoginError(error);
                  this.areCurrentRequestingAuthentication = false;
                }
              );
  }

  private manageSuccessfullLogin(res: GenericResponse<SuccessfulAuthenticationDTO>){
    let token: string = res.data.authToken;

    if(token==null || token.trim().length==0){
      this.showOnlyLoginError();
      return;
    }
    
    this.auth.saveAuthToken(token);

    this.router.navigateByUrl("/dashboard");
  }

  manageLoginError(error){
    let status: number = error.status;

    if(status==ChainExceptionHandler.SERVER_UNREACHABLE){
      this.showOnlyConnectionError();
      return;
    }
    if(status==ChainExceptionHandler.UNAUTHORIZED){
      this.showOnlyLoginError();
      return;
    }
    
    this.showOnlyConnectionError();
    this.exceptionHandler.manageError(status);
    return;

  }

  private hideAllErrors(): void{
    this.loginError = false;
    this.connectionError = false;
  }

  private showOnlyLoginError(): void{
    this.hideAllErrors()
    this.loginError = true;
  }

  private showOnlyConnectionError(): void{
    this.hideAllErrors()
    this.connectionError = true;
  }

}
