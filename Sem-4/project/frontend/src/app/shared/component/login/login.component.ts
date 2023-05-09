import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../service/auth.service';
import { TokenStorageService } from '../../service/token-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  public authForm!: FormGroup;
  public hasError: boolean = false;
  public errorMessage: string = '';

  constructor(private _auth: AuthService,
    private _formBuilder: FormBuilder,
    private _tokenStorage: TokenStorageService,
    private _router: Router) {

    this.authForm = this._buildAuthForm();
  }

  private _buildAuthForm(): FormGroup {
    return this._formBuilder.group({
      username: [null, Validators.required],
      password: [null, Validators.required],
    })
  }

  public onSubmit(): void {
    const { username, password } = this.authForm.value;
    this.hasError = false;

    this._auth.login(username, password).subscribe(
      data => {
        this._tokenStorage.saveToken(data.body.token);
        this._tokenStorage.saveUser(data.body.username);

        this._router.navigateByUrl('/post');
      },
      err => {
        this.hasError = true;
        this.errorMessage = err.error.message || err.error;
        ;
      }
    );
  }

  public onRegister(): void {
    this._router.navigateByUrl('register');
  }
}
