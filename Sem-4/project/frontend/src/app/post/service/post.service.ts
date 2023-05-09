import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from "rxjs/operators";
import { ApiResourceEnum } from 'src/app/shared/enum/api-resource.enum';
import { Post } from 'src/app/shared/interface/post.interface';
import { RequestService } from 'src/app/shared/service/request.service';

@Injectable({
  providedIn: 'root'
})
export class PostService extends RequestService {

  constructor(private _http: HttpClient) {
    super(ApiResourceEnum.Post)
  }

  public getAll(): Observable<Post[]> {

    return this._http.get(this._getUrl(), RequestService.baseHttpOptions)
      .pipe(
        map((response: any) => {
          return (response || []) as Post[];
        })
      )
  }

  public getById(id: number): Observable<Post> {

    return this._http.get(this._getUrl(id), RequestService.baseHttpOptions).pipe(
      map((res: any) => {
        return (res || {}) as Post;
      })
    );
  }

  public create(dto: Post): Observable<Post> {

    return this._http.post(this._getUrl(), dto, RequestService.baseHttpOptions).pipe(
      map((res: any) => {
        return (res || {}) as Post;
      })
    );
  }

  public update(dto: Post): Observable<Post> {

    return this._http.put(this._getUrl(), dto, RequestService.baseHttpOptions).pipe(
      map((res: any) => {
        return (res || {}) as Post;
      })
    );
  }

  public delete(id: number): Observable<any> {

    return this._http.delete(this._getUrl(id), RequestService.baseHttpOptions);
  }
}
