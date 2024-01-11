import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Post } from 'src/app/common/post';
import appConfig from 'src/app/config/app-config';
import { AuthService } from 'src/app/services/auth.service';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  appName: string = appConfig.appName;
  label: string = '';
  email: string = '';
  posts: Post[] = [];

  constructor(
    private postService: PostService, 
    private authService: AuthService,
    private route: ActivatedRoute,
    public router: Router) {}

  ngOnInit(): void {
    this.email = this.authService.getEmail();
    this.route.queryParamMap.subscribe((params) => {
      const searchParam = params.get('search');
      const emailParam = params.get('email');
      if (searchParam) {
        this.listSearchResults(searchParam);
      } else if (emailParam) {
        this.listPostsByEmail(emailParam);
      } else {
        this.listPosts();
      }
    });
  }

  listPosts() {
    this.postService.getPosts().subscribe(
      data => {
        this.posts = data;
        this.label = 'Latest Posts';
      }
    );
  }

  listSearchResults(keyword: string) {
    this.postService.searchForPosts(keyword).subscribe(
      data => {
        this.posts = data;
        this.label = `Search results for: ${keyword} (${this.posts.length})`;
      }
    );
  }

  listPostsByEmail(email: string) {
    this.postService.getPostsByAuthor(email).subscribe(
      data => {
        this.posts = data;
        this.label = `Posts by <em>${email}</em> (${this.posts.length})`;
      }
    );
  }

  createNewPost() {
    if (this.email === '') {
      window.alert("You must login before creating a new post!");
      return;
    }

    this.router.navigateByUrl('/new-post');
  }

  viewPost(postId: string) {
    this.router.navigateByUrl(`/post/${postId}`);
  }
}
