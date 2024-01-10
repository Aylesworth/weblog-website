import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Post } from 'src/app/common/post';
import { AuthService } from 'src/app/services/auth.service';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {
  post!: Post;
  email: string = '';

  constructor(
    private postService: PostService,
    private authService: AuthService,
    private route: ActivatedRoute,
    private router: Router) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => this.getPostDetails());
    this.email = this.authService.getEmail();
  }

  getPostDetails(): void {
    const id = this.route.snapshot.paramMap.get('id')!;
    this.postService.getPost(id).subscribe(
      data => this.post = data
    )
  }

  editPost() {
    this.router.navigateByUrl(`/post/${this.post.id!}/edit`);
  }

  deletePost() {
    const isConfirmed = window.confirm('Are you sure you want to permanently delete this post?');
    if (isConfirmed) {
      this.postService.deletePost(this.post.id!).subscribe(
        response => {
          console.log(response);
          this.router.navigate(['/home']);
        }
      );
    }
  }
}
