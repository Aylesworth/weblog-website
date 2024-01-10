import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import Editor from 'ckeditor5/build/ckeditor';
import { CookieService } from 'ngx-cookie-service';
import { Post } from 'src/app/common/post';
import { AuthService } from 'src/app/services/auth.service';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-edit-post',
  templateUrl: './edit-post.component.html',
  styleUrls: ['./edit-post.component.css']
})
export class EditPostComponent implements OnInit {
  public Editor = Editor;

  post!: Post;

  inputs = {
    title: '',
    content: '',
  }

  validation = {
    title: true,
    content: true,
  }

  email: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router, 
    private postService: PostService, 
    private cookieService: CookieService, 
    private authService: AuthService) {
  }

  ngOnInit(): void {
    this.email = this.cookieService.get('session_id');
    if (this.email === '') {
      this.authService.logout();
      this.router.navigate(['/home']);
    }
    this.route.paramMap.subscribe(() => {
      const postId: string = this.route.snapshot.paramMap.get('id')!;
      this.postService.getPost(postId).subscribe(
        data => {
          this.post = data;
          this.inputs.title = this.post.title!;
          this.inputs.content = this.post.content!;
        }
      );
    });
  }
  
  submit() {
    this.validation.title = this.inputs.title.trim() !== '';
    this.validation.content = this.inputs.content.trim() !== '';

    if (!this.validation.title || !this.validation.content) return;

    const post: Post = {
      author: this.email,
      title: this.inputs.title.trim(),
      content: this.inputs.content,
    };

    console.log(post);

    this.postService.updatePost(this.post.id!, post).subscribe(
      data => {
        console.log(data);  
        this.router.navigateByUrl(`/post/${this.post.id}`);
      }
    );
  }
}
