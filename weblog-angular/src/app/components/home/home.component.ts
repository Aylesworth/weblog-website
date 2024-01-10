import { Component, OnInit } from '@angular/core';
import { Post } from 'src/app/common/post';
import appConfig from 'src/app/config/app-config';
import { AuthService } from 'src/app/services/auth.service';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  appName: string = appConfig.appName;
  posts: Post[] = [];

  constructor(private postService: PostService, private authService: AuthService) {}

  ngOnInit(): void {
    this.authService.getEmail();
    this.postService.getPosts().subscribe(
      (data: any) => this.posts = data
    );
  }
}
