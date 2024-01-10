import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Comment } from 'src/app/common/comment';
import { User } from 'src/app/common/user';
import { AuthService } from 'src/app/services/auth.service';
import { CommentService } from 'src/app/services/comment.service';

@Component({
  selector: 'app-comment-section',
  templateUrl: './comment-section.component.html',
  styleUrls: ['./comment-section.component.css']
})
export class CommentSectionComponent implements OnInit {
  @Input() postId!: string;

  comments: Comment[] = [];
  repliesHidden: boolean[] = [];
  email: string = '';
  self?: User;

  constructor(private commentService: CommentService, private authService: AuthService) {}

  ngOnInit(): void {
    this.email = this.authService.getEmail();
    if (this.email !== '') {
      this.authService.getUserByEmail(this.email).subscribe(
        data => this.self = data
      );
    }
    this.fetchComments();
  }

  fetchComments() {
    this.commentService.getComments(this.postId, this.email).subscribe(
      data => {
        console.log(data);
        this.comments = data;
        this.repliesHidden = new Array(this.comments.length).fill(true);
      }
    );
  }

  fetchReplies(comment: Comment) {
    this.commentService.getReplies(comment.id!, this.email).subscribe(
      data => {
        console.log(data);
        comment.replies = data;
        comment.totalReplies = data.length;
      }
    );
  }

  submitComment(commentForm: NgForm) {
    const comment: Comment = {
      email: this.email,
      content: commentForm.controls['comment'].value.trim()
    }

    console.log(comment);

    this.commentService.addComment(this.postId, comment).subscribe(
      response => {
        console.log(response);
        this.fetchComments();
      }
    );
    commentForm.controls['comment'].setValue('');
  }

  toggleReplies(index: number) {
    this.repliesHidden[index] = !this.repliesHidden[index];
    const comment = this.comments[index];
    if (this.repliesHidden[index]) {
      comment.replies = [];
    } else {
      this.fetchReplies(comment);
    }
  }

  toggleLike(comment: Comment) {
    comment.liked = !comment.liked;

    if (comment.liked) {
      this.commentService.likeComment(comment.id!, this.email).subscribe(
        newLikes => comment.likes = newLikes
      );
    } else {
      this.commentService.unlikeComment(comment.id!, this.email).subscribe(
        newLikes => comment.likes = newLikes
      );
    }
  }

  submitReply(comment: Comment, replyForm: NgForm) {
    const reply: Comment = {
      email: this.email,
      content: replyForm.controls['reply'].value.trim()
    }

    this.commentService.replyComment(comment.id!, reply).subscribe(
      response => {
        console.log(response);
        this.fetchReplies(comment);
      }
    )
    replyForm.controls['reply'].setValue('');
  }
}
