import { Reaction, ReactionType } from './../../../shared/interface/reaction.interface';
import { Component, EventEmitter, Input, Output, OnInit } from '@angular/core';
import { Post } from 'src/app/shared/interface/post.interface';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
export class PostComponent implements OnInit {

  @Input() post: Post | undefined;

  @Output() onDeleteEvent: EventEmitter<Post> = new EventEmitter();
  @Output() onUpdateEvent: EventEmitter<Post> = new EventEmitter();
  @Output() onReactionEvent: EventEmitter<Reaction> = new EventEmitter();
  @Output() onCommentEvent: EventEmitter<Post> = new EventEmitter();

  public likeCounter: number = 0;
  public loveCounter: number = 0;
  public smileCounter: number = 0;
  public commentCounter: number = 0;
  public showAction: boolean = false;

  ngOnInit(): void {
    if (this.post && this.post.reactionMap) {
      if (this.post.reactionMap.hasOwnProperty('LIKE'))
        this.likeCounter = this.post.reactionMap.LIKE;

      if (this.post.reactionMap.hasOwnProperty('LOVE'))
        this.loveCounter = this.post.reactionMap.LOVE;

      if (this.post.reactionMap.hasOwnProperty('SMILE'))
        this.smileCounter = this.post.reactionMap.SMILE;
    }

    this.commentCounter = this.post?.totalComment || 0;
    this.showAction = true;
  }

  public onDelete(): void {
    if (this.post)
      this.onDeleteEvent.emit(this.post);
  }

  public onUpdate(): void {
    if (this.post)
      this.onUpdateEvent.emit(this.post);
  }

  public onCommentClick(): void {
    if (this.post)
      this.onCommentEvent.emit(this.post);
  }

  public onAddReaction(reaction: ReactionType): void {
    if (reaction && this.post?.id) {

      const dto: Reaction = {
        reaction: reaction,
        postId: this.post?.id
      }

      this.onReactionEvent.emit(dto);
    }
  }
}