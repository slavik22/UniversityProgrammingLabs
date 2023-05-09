import { Injectable } from '@angular/core';
import { Subject, Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class TokenStorageService {

    private readonly _TOKEN_KEY = 'auth-token';
    private readonly _USER_KEY = 'auth-user';

    private _userSubject$: Subject<string> = new Subject();

    signOut(): void {
        window.sessionStorage.clear();
    }

    public saveToken(token: string): void {
        window.sessionStorage.removeItem(this._TOKEN_KEY);
        window.sessionStorage.setItem(this._TOKEN_KEY, token);
    }

    public getToken(): string | null {
        return window.sessionStorage.getItem(this._TOKEN_KEY);
    }

    public saveUser(user: string): void {
        window.sessionStorage.removeItem(this._USER_KEY);
        window.sessionStorage.setItem(this._USER_KEY, JSON.stringify(user));
        this._userSubject$.next(user);
    }

    public getUser(): string | null {
        const user = window.sessionStorage.getItem(this._USER_KEY);
        if (user) {
            return JSON.parse(user);
        }

        return null;
    }

    public getUserObs(): Observable<string> {

        return this._userSubject$.asObservable();
    }
}
