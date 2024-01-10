import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import appConfig from 'src/app/config/app-config';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {
  appName: string = appConfig.appName;
  authUrl: string = `https://accounts.google.com/o/oauth2/auth?response_type=code&client_id=${appConfig.clientId}&redirect_uri=${appConfig.redirectUri}&scope=profile%20email%20openid&approval_prompt=force`;
  loggedIn: boolean = false;
  email: string = '';

  constructor(
    private authService: AuthService,
    private router: Router) {}

  ngOnInit(): void {
    this.authService.loggedIn.subscribe(
      value => {
        this.loggedIn = value;
        if (value) 
          this.email = this.authService.getEmail();
      }
    );

    this.email = this.authService.getEmail();
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/home']);
  }
}
