import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import appConfig from '../config/app-config';
import { Observable, map } from 'rxjs';
import { Comment } from '../common/comment';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  postUrl: string = `${appConfig.apiUrl}/posts`

  constructor(private httpClient: HttpClient) { }

  getComments(postId: string): Observable<Comment[]> {
    const url: string = `${this.postUrl}/${postId}/comments`;
    return this.httpClient.get<Comment[]>(url).pipe(
      map((response: any) => {
          for (let element of response) {
            element.posted = new Date(element.posted);
          }
          return response;
      })
    );
  }

  addComment(postId: string, comment: Comment): Observable<any> {
    const url: string = `${this.postUrl}/${postId}/comments`;
    console.log("Post: " + url);
    return this.httpClient.post<any>(url, comment);
  }
}
