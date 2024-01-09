import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-callback',
  templateUrl: './callback.component.html',
  styleUrls: ['./callback.component.css']
})
export class CallbackComponent implements OnInit {
  constructor(
    private authService: AuthService, 
    private cookieService: CookieService, 
    private route: ActivatedRoute,
    private router: Router) {}

  ngOnInit(): void {
    this.route.queryParamMap.subscribe((params) => {
      const code = params.get('code')!;
      
      this.authService.getAccessToken(code).subscribe(
        data => {
          console.log(data);
          const accessToken: string = data.access_token;
          const expiresIn: number = data.expires_in;
          const tokenType: string = data.token_type;
          const idToken: string = data.id_token;
          
          const decodedId = JSON.parse(atob(idToken.split('.')[1]));
          console.log(decodedId);

          const email: string = decodedId.email;

          this.cookieService.set('session_id', email, expiresIn, '/');
          this.cookieService.set('access_token', accessToken, expiresIn, '/');

          this.authService.login();

          this.router.navigateByUrl('/home');
        }
      );
    });
  }
}
