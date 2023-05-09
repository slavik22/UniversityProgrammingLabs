import { Reaction, ReactionType } from './../../../shared/interface/reaction.interface';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Post } from 'src/app/shared/interface/post.interface';
@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.scss']
})
export class PostListComponent {

  @Input() postList: Post[] = [];

  @Output() onDeleteEvent: EventEmitter<Post> = new EventEmitter();
  @Output() onUpdateEvent: EventEmitter<Post> = new EventEmitter();
  @Output() onReactionEvent: EventEmitter<Reaction> = new EventEmitter();
  @Output() onCommentEvent: EventEmitter<Post> = new EventEmitter();

  public onDelete(post: Post): void {
    this.onDeleteEvent.emit(post);
  }

  public onUpdate(post: Post): void {
    this.onUpdateEvent.emit(post);
  }

  public onReaction(reaction: Reaction): void {
    this.onReactionEvent.emit(reaction);
  }

  public onComment(post: Post): void {
    this.onCommentEvent.emit(post);
  }
}
