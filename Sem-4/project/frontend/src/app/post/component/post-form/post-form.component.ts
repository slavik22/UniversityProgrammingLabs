import { Component, OnInit, Output, EventEmitter, Input, Inject } from '@angular/core';
import { FormBuilder, FormGroup, FormGroupDirective, Validators } from '@angular/forms';
import { Post } from 'src/app/shared/interface/post.interface';

@Component({
  selector: 'app-post-form',
  templateUrl: './post-form.component.html',
  styleUrls: ['./post-form.component.scss']
})
export class PostFormComponent implements OnInit {

  @Input() dto?: Post;
  @Input() isEdit: boolean = false;

  @Output() onPostEvent: EventEmitter<any> = new EventEmitter();
  @Output() onCancelEvent: EventEmitter<any> = new EventEmitter();

  public postForm!: FormGroup;
  public title!: string;
  public saveLabel!: string;
  public cancelLabel!: string;

  constructor(private _formBuilder: FormBuilder) {

    this.postForm = this._buildPostForm();
  }

  ngOnInit(): void {
    if (this.dto && this.isEdit) {
      this._setCategoryFormValues(this.dto);
      this.title = "Update post";
      this.cancelLabel = "Cancel";
      this.saveLabel = "Save";
    } else {
      this.title = "Create new post";
      this.saveLabel = "Create post";
    }
  }

  public onFormSubit(formDirective: FormGroupDirective): void {

    const dto: Post = this.postForm.value;

    this.onPostEvent.emit(dto);

    formDirective.resetForm();
    this.postForm.reset();
    this.postForm.markAsPristine();
    this.postForm.markAsUntouched();
  }

  public onCancel(): void {
    this.onCancelEvent.emit();
  }

  private _buildPostForm(): FormGroup {
    return this._formBuilder.group({
      id: [null],
      version: [null],
      creationTimestamp: [null],
      updateTimestamp: [null],
      creationUser: [null],
      updateUser: [null],
      title: [null, Validators.required],
      description: [null, Validators.required],
    })
  }

  private _setCategoryFormValues(dto: Post): void {
    this.postForm?.setValue({
      id: dto?.id,
      version: dto?.version,
      creationTimestamp: dto?.creationTimestamp,
      updateTimestamp: dto?.updateTimestamp,
      creationUser: dto?.creationUser,
      updateUser: dto?.updateUser,
      description: dto?.description,
      title: dto?.title
    })
  }

}
