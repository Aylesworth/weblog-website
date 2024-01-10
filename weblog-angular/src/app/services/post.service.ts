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
    return this.httpClient.post<any>(this.postUrl, post);
  }

  getPosts(): Observable<Post[]> {
    return this.httpClient.get<Post[]>(this.postUrl);
  }

  getPost(id: string): Observable<Post> {
    return this.httpClient.get<Post>(`${this.postUrl}/${id}`);
  }

  updatePost(id: string, post: Post): Observable<any> {
    return this.httpClient.put<Post>(`${this.postUrl}/${id}`, post);
  }

  deletePost(id: string): Observable<any> {
    return this.httpClient.delete<Post>(`${this.postUrl}/${id}`);
  }

  searchForPosts(keyword: string): Observable<Post[]> {
    return this.httpClient.get<Post[]>(`${this.postUrl}?search=${keyword}`);
  }

  getPostsByAuthor(email: string): Observable<Post[]> {
    return this.httpClient.get<Post[]>(`${this.postUrl}?email=${email}`);
  }
}
