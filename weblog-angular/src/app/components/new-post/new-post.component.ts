import { Component } from '@angular/core';
import { Router } from '@angular/router';
import Editor from 'ckeditor5/build/ckeditor';
import { Post } from 'src/app/common/post';
import { PostService } from 'src/app/services/post.service';
    
@Component({
  selector: 'app-new-post',
  templateUrl: './new-post.component.html',
  styleUrls: ['./new-post.component.css']
})
export class NewPostComponent {
  public Editor = Editor;

  inputs = {
    title: '',
    content: '',
  }

  constructor(private router: Router, private postService: PostService) {
  }
  
  submit() {
    const post: Post = {
      id: 0,
      title: this.inputs.title,
      content: this.inputs.content,
      author: 'example@gmail.com',
      thumbnail: '',
      created: '',
      updated: ''
    };

    this.postService.createPost(post).subscribe(
      data => console.log(data)
    );

    this.router.navigateByUrl('/home');
  }
}
