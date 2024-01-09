import { Component } from '@angular/core';
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

  constructor(private postService: PostService) {
  }
  
  submit() {
    console.log(this.inputs.title);
    console.log(this.inputs.content);
    const post: Post = {
      id: 0,
      title: this.inputs.title,
      content: this.inputs.content,
      author: 'example@gmail.com',
      created: '',
      updated: ''
    };

    this.postService.createPost(post).subscribe(
      data => console.log(data)
    );
  }
}
