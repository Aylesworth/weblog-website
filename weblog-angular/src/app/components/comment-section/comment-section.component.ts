import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Comment } from 'src/app/common/comment';
import { AuthService } from 'src/app/services/auth.service';
import { CommentService } from 'src/app/services/comment.service';

@Component({
  selector: 'app-comment-section',
  templateUrl: './comment-section.component.html',
  styleUrls: ['./comment-section.component.css']
})
export class CommentSectionComponent implements OnInit {
  @Input() postId!: string;
  @ViewChild('commentForm') commentForm!: NgForm;

  comments: Comment[] = [];
  repliesHidden: boolean[] = [];
  email: string = '';

  constructor(private commentService: CommentService, private authService: AuthService) {}

  ngOnInit(): void {
    this.fetchComments();
    this.email = this.authService.getEmail();
  }

  fetchComments() {
    this.commentService.getComments(this.postId).subscribe(
      data => {
        console.log(data);
        this.comments = data;
        this.repliesHidden = new Array(this.comments.length).fill(true);
      }
    );
  }

  submitComment() {
    const comment: Comment = {
      email: this.email,
      content: this.commentForm.controls['comment']!.value
    }

    console.log(comment);

    this.commentService.addComment(this.postId, comment).subscribe(
      response => {
        console.log(response);
        this.fetchComments();
      }
    );
    this.commentForm.controls['comment']!.setValue('');
  }

  toggleReplies(index: number) {
    this.repliesHidden[index] = !this.repliesHidden[index];
  }
}
