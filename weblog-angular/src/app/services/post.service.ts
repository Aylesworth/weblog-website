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

  createPost(post: Post): Observable<any> {
    return this.httpClient.post<Post>(this.postUrl, post);
  }

  getPosts(): Observable<Post[]> {
    return this.httpClient.get<Post[]>(this.postUrl).pipe(
      map((response: any) => {
        for (let element of response) {
          console.log(element);
          element.created = new Date(element.created);
          element.updated = new Date(element.updated);
        }
        return response;
      })
    );
  }

  getPost(id: string): Observable<Post> {
    return this.httpClient.get<Post>(`${this.postUrl}/${id}`);
  }
}
