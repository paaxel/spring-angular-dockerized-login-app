import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";


@Injectable()
export class BackendUrlsService {
  
  constructor() {
  }

  private getBackendUrl(): string {
    return environment.API_URL.trim();
  }

  get baseURL() {
    if (!this.getBackendUrl().endsWith('/')) {
      return this.getBackendUrl() + '/';
    }
    return this.getBackendUrl();
  }

  getLoginUrl() {
    return this.baseURL + environment.LOGIN_PATH;
  }

  getProfileWsUrl(): any {
    return this.baseURL + environment.PROFILE_WS_URL;
  }
}