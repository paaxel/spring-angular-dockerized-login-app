import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { AuthService } from 'src/app/model/services/auth.service';


@Component({
  selector: 'app-navbar-placeholder',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})

export class NavbarComponent implements OnInit {

  constructor(private auth: AuthService, private router: Router) {

  }

  ngOnInit() {
  }


  executeLogout(): void {
    this.auth.logout();
    this.router.navigateByUrl("/login");
  }

}