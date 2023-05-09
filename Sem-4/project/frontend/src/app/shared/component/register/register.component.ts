import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../service/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  public authForm!: FormGroup;
  public hasError: boolean = false;
  public errorMessage: string = '';

  constructor(private _auth: AuthService,
    private _formBuilder: FormBuilder,
    private _router: Router) {
    this.authForm = this._buildAuthForm();
  }

  public onSubmit(): void {
    const { username, email, password } = this.authForm.value;
    this.hasError = false;

    this._auth.register(username, email, password).subscribe(
      data => {
        this._router.navigateByUrl('/post');
      },
      err => {
        this.hasError = true;
        this.errorMessage = err.error.message;
      }
    );
  }

  public onLogin(): void {
    this._router.navigateByUrl('login');
  }

  private _buildAuthForm(): FormGroup {
    return this._formBuilder.group({
      username: [null, Validators.required],
      email: [null, Validators.required],
      password: [null, Validators.required],
    })
  }

}
