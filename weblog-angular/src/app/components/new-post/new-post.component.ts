import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Editor from 'ckeditor5/build/ckeditor';
import { CookieService } from 'ngx-cookie-service';
import { Post } from 'src/app/common/post';
import { AuthService } from 'src/app/services/auth.service';
import { PostService } from 'src/app/services/post.service';
    
@Component({
  selector: 'app-new-post',
  templateUrl: './new-post.component.html',
  styleUrls: ['./new-post.component.css']
})
export class NewPostComponent implements OnInit {
  public Editor = Editor;

  inputs = {
    title: '',
    content: '',
  }

  email: string = '';

  constructor(
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
  }
  
  submit() {
    const post: Post = {
      title: this.inputs.title,
      content: this.inputs.content,
      author: this.email,
    };

    console.log(post);

    this.postService.createPost(post).subscribe(
      data => console.log(data)
    );

    this.router.navigateByUrl('/home');
  }
}
