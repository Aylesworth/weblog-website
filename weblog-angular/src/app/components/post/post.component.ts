import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Post } from 'src/app/common/post';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent {
  post!: Post;

  constructor(
    private postService: PostService,
    private route: ActivatedRoute) {
      const id = this.route.snapshot.paramMap.get('id')!;
      postService.getPost(id).subscribe(
        data => this.post = data
      )
    }
}
