import { Component } from '@angular/core';
import { Post } from 'src/app/common/post';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  posts: Post[] = [];

  constructor(private postService: PostService) {
    postService.getPosts().subscribe(
      data => {
        this.posts = data;
      }
    );
  }
}
