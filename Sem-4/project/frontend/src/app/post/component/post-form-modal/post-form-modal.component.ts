import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Post } from 'src/app/shared/interface/post.interface';

@Component({
  selector: 'app-post-form-modal',
  templateUrl: './post-form-modal.component.html',
  styleUrls: ['./post-form-modal.component.scss']
})
export class PostFormModalComponent {

  public post: Post | undefined;

  constructor(public dialogRef: MatDialogRef<PostFormModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data?: any) {
      this.post = data.post;
     }

  public onCancel(): void {
    this.dialogRef.close();
  }

  public onSave(dto: Post): void {
    this.dialogRef.close(dto);
  }
}
