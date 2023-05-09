import {Injectable} from '@angular/core';
import {RequestService} from "./request.service";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import { ApiResourceEnum } from '../enum/api-resource.enum';

@Injectable({
    providedIn: 'root'
})
export class AuthService extends RequestService{

    constructor(private _http: HttpClient) {
        super(ApiResourceEnum.Auth);
    }

    login(username: string, password: string): Observable<any> {
        return this._http.post(this._getUrl().concat('/login'), {
            username,
            password
        }, RequestService.baseHttpOptions);
    }

    register(username: string, email: string, password: string): Observable<any> {
        return this._http.post(this._getUrl().concat('/register'), {
            username,
            email,
            password
        }, RequestService.baseHttpOptions);
    }
}
