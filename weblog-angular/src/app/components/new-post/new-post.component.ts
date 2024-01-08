import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-new-post',
  templateUrl: './new-post.component.html',
  styleUrls: ['./new-post.component.css']
})
export class NewPostComponent {
  @Input() placeholder = '';

  value: string = '';

  onChange: any = () => {};
  onTouch: any = () => {};

  writeValue(obj: any): void {
    this.value = obj;
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouch = fn;
  }

  onContentChanged({ html, text }: { html: string; text: string }): void {
    this.value = html;
    this.onChange(html);
    this.onTouch();
  }
}
