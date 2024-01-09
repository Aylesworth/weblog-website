import { Component } from '@angular/core';
import appConfig from './config/app-config';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  appName: string = appConfig.appName;
}
