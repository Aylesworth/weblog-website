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
  authUrl: string = `https://accounts.google.com/o/oauth2/auth?response_type=code&client_id=${appConfig.clientId}&redirect_uri=${appConfig.redirectUri}&scope=${appConfig.scopes.join('%20')}&approval_prompt=force`;
  loggedIn!: boolean;
  name: string = '';
  searchInput: string = '';

  constructor(
    private authService: AuthService,
    private router: Router) {}

  ngOnInit(): void {
    this.authService.loggedIn.subscribe(
      value => {
        this.loggedIn = value;
        if (value) {
          const email = this.authService.getEmail();
          this.authService.getUserByEmail(email).subscribe(
            data => this.name = data.name!
          );
        }
      }
    );
  }
  
  search() {
    this.router.navigateByUrl(`/home?search=${this.searchInput.trim()}`);
  }

  logout() {
    const isConfirmed = window.confirm('Are you sure you want to log out?');
    if (isConfirmed) {
      this.authService.logout();
      window.location.reload();
    }
  }
}
