import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor, HTTP_INTERCEPTORS
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenStorageService } from './token-storage.service';



@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  private readonly _TOKEN_HEADER_KEY = 'Authorization';

  constructor(private _token: TokenStorageService) {}

  intercept(req: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
      let authReq = req;
      const token = this._token.getToken();
      if (token && token != "undefined" && !this._needIgnoreToken(req.url)) {
          authReq = req.clone({ headers: req.headers.set(this._TOKEN_HEADER_KEY, 'Bearer ' + token) });
      }
      return next.handle(authReq);
  }

  private _needIgnoreToken(url: string): boolean {
    return url.includes("auth/register") || url.includes("auth/login");
  }
}

export const AuthInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
];
