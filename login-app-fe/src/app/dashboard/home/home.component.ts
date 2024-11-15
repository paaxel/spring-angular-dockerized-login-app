import { Component, OnInit } from '@angular/core';
import {  HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { HomeService } from '../../model/services/home.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  

  constructor(private http: HttpClient, private homeService: HomeService) { 
                  
  }

  ngOnInit() {
  }

  private get userProfile(){
    return this.homeService.userProfile;
  }

  get name(){
    if(this.userProfile==null){
      return ''
    }
    return this.userProfile.name
  }
  get surname(){
    if(this.userProfile==null){
      return ''
    }
    return this.userProfile.surname
  }

  get userId(){
    if(this.userProfile==null){
      return ''
    }
    return this.userProfile.id
  }

  get registrationDate(){
    if(this.userProfile==null || this.userProfile.registrationDate==null){
      return ''
    }
    return new Date(this.userProfile.registrationDate)
  }

}
