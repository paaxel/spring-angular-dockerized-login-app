import { Injectable, NgZone } from "@angular/core";
import { Observable, of, forkJoin } from "rxjs";
import { Router } from "@angular/router";
import { RestDataSource } from "../rest.datasource";
import { JwtHelper } from "src/app/utils/jwt-helper.model";
import { GenericResponse } from "../dto/generic-response.model";
import { AuthenticationDTO } from "../dto/authentication-dto.model";
import { HttpHeaders, HttpParams } from "@angular/common/http";
import { BackendUrlsService } from "../backend-urls.service";
import { SuccessfulAuthenticationDTO } from "../dto/successfull-authentication.model";

@Injectable()
export class AuthService {


  jwtHelper: JwtHelper;

  constructor(private router: Router, private datasource: RestDataSource,
    private zone: NgZone, private backendUrlSrv: BackendUrlsService) {
    this.jwtHelper = new JwtHelper();
  }

  authenticate(username: string, password: string) {
    let params: HttpParams = new HttpParams();
    let authDTO: AuthenticationDTO = new AuthenticationDTO(username, password);

    return this.datasource.makePostJsonObject<GenericResponse<SuccessfulAuthenticationDTO>>
      (this.backendUrlSrv.getLoginUrl(), authDTO, params, new HttpHeaders(), false,
        false, false);
  }

  saveAuthToken(token: string) {
    if (token != null) {
      if (token.startsWith("Bearer ")) {
        this.datasource.saveAuthToken(token.substring(7, token.length));
        return;
      }
    }
    this.datasource.saveAuthToken(token);
  }

  get username(): string {
    return this.datasource.username;
  }

  get authenticated(): boolean {
    return this.isCurrentTokenValid();
  }

  logout(): void {
    this.datasource.deleteAuthToken();
  }

  redirectToLogin(): void {
    this.router.navigateByUrl("/login");
  }

  reloadPage() { // click handler or similar
    this.zone.runOutsideAngular(() => {
      location.reload();
    });
  }

  public isCurrentTokenValid(): boolean {
    //console.log(this.getAuthToken());
    if (this.datasource.getAuthToken() == null || this.datasource.getAuthToken() == "null") {
      return false;
    }
    return !this.isCurrentTokenExpired();
  }

  isCurrentTokenExpired(): boolean {
    if (this.datasource.isCurrentTokenExpired()) {
      this.datasource.deleteAuthToken();
      return true;
    }
    return false;
  }


}