import { BaseModel } from "./base-model.interface";

export interface Comment extends BaseModel{
    text: string;
    postId?: number;
}