import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { PostComponent } from './components/post/post.component';
import { NewPostComponent } from './components/new-post/new-post.component';
import { CallbackComponent } from './components/callback/callback.component';
import { EditPostComponent } from './components/edit-post/edit-post.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'new-post', component: NewPostComponent },
  { path: 'post/:id', component: PostComponent },
  { path: 'post/:id/edit', component: EditPostComponent },
  { path: 'callback', component: CallbackComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: '**', redirectTo: '/home', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
