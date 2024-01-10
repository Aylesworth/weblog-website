import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { NewPostComponent } from './components/new-post/new-post.component';
import { PostComponent } from './components/post/post.component';
import { FormsModule } from '@angular/forms';
import { CKEditorModule } from '@ckeditor/ckeditor5-angular';
import { CommentSectionComponent } from './components/comment-section/comment-section.component';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { CallbackComponent } from './components/callback/callback.component';
import { CookieService } from 'ngx-cookie-service';
import { EditPostComponent } from './components/edit-post/edit-post.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NewPostComponent,
    PostComponent,
    CommentSectionComponent,
    NavBarComponent,
    CallbackComponent,
    EditPostComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    CKEditorModule
  ],
  providers: [CookieService],
  bootstrap: [AppComponent]
})
export class AppModule { }