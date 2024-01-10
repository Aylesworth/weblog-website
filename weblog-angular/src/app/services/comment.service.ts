import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import appConfig from '../config/app-config';
import { Observable, map } from 'rxjs';
import { Comment } from '../common/comment';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  postUrl: string = `${appConfig.apiUrl}/posts`;
  commentUrl: string = `${appConfig.apiUrl}/comments`;

  constructor(private httpClient: HttpClient) { }

  getComments(postId: string, email: string): Observable<Comment[]> {
    const url: string = `${this.postUrl}/${postId}/comments?email=${email}`;
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
    return this.httpClient.post<any>(url, comment);
  }

  likeComment(commentId: string, email: string): Observable<number> {
    const url: string = `${this.commentUrl}/${commentId}/like`;
    return this.httpClient.post<number>(url, {
      email: email
    });
  }

  unlikeComment(commentId: string, email: string): Observable<number> {
    const url: string = `${this.commentUrl}/${commentId}/unlike`;
    return this.httpClient.post<number>(url, {
      email: email
    });
  }

  replyComment(commentId: string, reply: Comment): Observable<any> {
    const url: string = `${this.commentUrl}/${commentId}/replies`;
    return this.httpClient.post<any>(url, reply);
  }

  getReplies(commentId: string, email: string): Observable<Comment[]> {
    const url: string = `${this.commentUrl}/${commentId}/replies?email=${email}`;
    return this.httpClient.get<Comment[]>(url).pipe(
      map((response: any) => {
        for (let element of response) {
          element.posted = new Date(element.posted);
        }
        return response;
      })
    );
  }

  deleteComment(commentId: string): Observable<any> {
    const url: string = `${this.commentUrl}/${commentId}`;
    return this.httpClient.delete<any>(url);
  }
}
