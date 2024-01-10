import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import appConfig from '../config/app-config';
import { BehaviorSubject, Observable } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';
import { User } from '../common/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  loggedIn!: BehaviorSubject<boolean>;

  constructor(private httpClient: HttpClient, private cookieService: CookieService) {
    const email: string = this.cookieService.get('session_id');
    this.loggedIn = new BehaviorSubject<boolean>(email !== '');
  }

  getAccessToken(code: string): Observable<any> {
    const tokenUrl: string = 'https://accounts.google.com/o/oauth2/token';
    const headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded');
    const body = `grant_type=authorization_code&code=${code}&redirect_uri=${appConfig.redirectUri}&client_id=${appConfig.clientId}&client_secret=${appConfig.clientSecret}`;
    return this.httpClient.post<any>(tokenUrl, body, { headers });
  }

  getUserInfo(accessToken: string): Observable<any> {
    const url = 'https://www.googleapis.com/oauth2/v1/userinfo?alt=json';
    return this.httpClient.get<any>(url, { headers: new HttpHeaders().set('Authorization', `Bearer ${accessToken}`) });
  }

  saveUser(user: User): Observable<any> {
    const url = `${appConfig.apiUrl}/users`;
    return this.httpClient.post<any>(url, user);
  }

  getUserByEmail(email: string): Observable<User> {
    const url = `${appConfig.apiUrl}/users/${email}`;
    return this.httpClient.get<User>(url);
  }

  setAuthInfo(email: string, accessToken: string, expiresIn: number) {
    const expires: Date = new Date();
    expires.setSeconds(expires.getSeconds() + expiresIn);

    this.cookieService.set('session_id', email, expires);
    this.cookieService.set('access_token', accessToken, expires);

    this.loggedIn.next(true);
  }

  getEmail(): string {
    const email: string = this.cookieService.get('session_id');
    if (email === '') {
      this.logout();
    }
    return email;
  }

  logout() {
    this.cookieService.delete('session_id');
    this.cookieService.delete('access_token');

    this.loggedIn.next(false);
  }
}
