import { BaseModel } from "./base-model.interface";
import { ReactionType } from "./reaction.interface";

export interface Post extends BaseModel {
    title: string;
    description?: string;
    reactionMap?: any;
    totalComment?: number;
}
