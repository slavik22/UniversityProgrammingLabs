import { Reaction } from './../../shared/interface/reaction.interface';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RequestService } from 'src/app/shared/service/request.service';
import { map } from 'rxjs/operators';
import { ApiResourceEnum } from 'src/app/shared/enum/api-resource.enum';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReactionService extends RequestService {

  constructor(private _http: HttpClient) {
    super(ApiResourceEnum.Reaction)
   }

  public create(dto: Reaction): Observable<Reaction> {

    return this._http.post(super._getUrl(), dto, RequestService.baseHttpOptions).pipe(
      map((res: any) => {
        return (res || {}) as Reaction;
      })
    );
  }
}
