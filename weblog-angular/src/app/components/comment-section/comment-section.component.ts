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
  liked: boolean[] = [];
  repliesHidden: boolean[] = [];
  email: string = '';

  constructor(private commentService: CommentService, private authService: AuthService) {}

  ngOnInit(): void {
    this.email = this.authService.getEmail();
    this.fetchComments();
  }

  fetchComments() {
    this.commentService.getComments(this.postId, this.email).subscribe(
      data => {
        console.log(data);
        this.comments = data;
        this.liked = this.comments.map(comment => comment.liked!);
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

  toggleLike(index: number) {
    this.liked[index] = !this.liked[index];
    const comment = this.comments[index];

    if (this.liked[index]) {
      this.commentService.likeComment(comment.id!, this.email).subscribe(
        newLikes => comment.likes = newLikes
      );
    } else {
      this.commentService.unlikeComment(comment.id!, this.email).subscribe(
        newLikes => comment.likes = newLikes
      );
    }
  }

  like(comment: Comment) {
  }
}
