
export interface Reaction {
    reaction: ReactionType;
    count?: number;
    postId: number;
}

export type ReactionType = 'LIKE' | 'SMILE' | 'LOVE';