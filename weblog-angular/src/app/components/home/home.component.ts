import { Component } from '@angular/core';
import { Post } from 'src/app/common/post';
import appConfig from 'src/app/config/app-config';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  appName: string = appConfig.appName;
  posts: Post[] = [];

  constructor(private postService: PostService) {
    postService.getPosts().subscribe(
      (data: any) => this.posts = data
    );
  }
}
