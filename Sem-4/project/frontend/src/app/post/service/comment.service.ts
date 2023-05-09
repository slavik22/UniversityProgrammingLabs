import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ApiResourceEnum } from 'src/app/shared/enum/api-resource.enum';
import { Comment } from 'src/app/shared/interface/comment.interface';
import { RequestService } from 'src/app/shared/service/request.service';

@Injectable({
  providedIn: 'root'
})
export class CommentService extends RequestService {

  constructor(private _http: HttpClient) {
    super(ApiResourceEnum.Comment)
  }

  public create(dto: Comment): Observable<Comment> {

    return this._http.post(super._getUrl(), dto, RequestService.baseHttpOptions).pipe(
      map((res: any) => {
        return (res || {}) as Comment;
      })
    );
  }

  public getCommentsByPostId(postId: number): Observable<Comment[]> {

    return this._http.get(super._getUrl() + '/post/' + postId, RequestService.baseHttpOptions).pipe(
      map((res: any) => {
        return (res || []) as Comment[];
      })
    );
  }
}
