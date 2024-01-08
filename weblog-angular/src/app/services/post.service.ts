import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import config from '../config/app-config';
import appConfig from '../config/app-config';
import { Observable, map } from 'rxjs';
import { Post } from '../common/post';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private postUrl: string = `${appConfig.apiUrl}/posts`;

  constructor(private httpClient: HttpClient) { }

  getPosts(): Observable<Post[]> {
    return this.httpClient.get<Post[]>(this.postUrl).pipe(
      map((response: any) => {
        for (let element of response) {
          element.created = new Date(element.created[0], element.created[1] - 1, element.created[2], element.created[3], element.created[4], element.created[5]);
          element.updated = new Date(element.updated[0], element.updated[1] - 1, element.updated[2], element.updated[3], element.updated[4], element.updated[5]);
        }
        return response;
      })
    );
  }
}
