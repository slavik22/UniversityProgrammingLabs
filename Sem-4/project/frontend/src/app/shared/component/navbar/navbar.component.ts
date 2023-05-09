import { TokenStorageService } from './../../service/token-storage.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit, OnDestroy {

  public username?: string;

  private _userSub: Subscription = Subscription.EMPTY;

  constructor(private _router: Router,
    private _tokenStorage: TokenStorageService) { }
  

  ngOnInit(): void {

    this._tokenStorage.getUserObs().subscribe(username => this.username = username);
  }

  ngOnDestroy(): void {
    this._userSub.unsubscribe();
  }

  public login(): void {
    this._router.navigateByUrl('/login');
  }

  public logout(): void {
    this._tokenStorage.signOut();
    window.location.reload();
  }
}
