import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import appConfig from '../config/app-config';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  loggedIn: BehaviorSubject<boolean> = new BehaviorSubject(false);

  constructor(private httpClient: HttpClient) { }

  getAccessToken(code: string): Observable<any> {
    const tokenUrl: string = 'https://accounts.google.com/o/oauth2/token';
    const headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded');
    const body = `grant_type=authorization_code&code=${code}&redirect_uri=${appConfig.redirectUri}&client_id=${appConfig.clientId}&client_secret=${appConfig.clientSecret}`;
    return this.httpClient.post<any>(tokenUrl, body, { headers });
  }

  login() {
    this.loggedIn.next(true);
  }

  logout() {
    this.loggedIn.next(false);
  }
}
