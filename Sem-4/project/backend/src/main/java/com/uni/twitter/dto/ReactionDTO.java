package com.bozzaccio.twitterclone.dto;

import com.bozzaccio.twitterclone.entity.Reaction;
import com.bozzaccio.twitterclone.util.ReactionEnum;

public class ReactionDTO extends AbstractDTO<Long> {

    private static final long serialVersionUID = 5378990204298854441L;

    private ReactionEnum reaction;
    private Long postId;


    public ReactionDTO() {
    }

    public ReactionDTO(Reaction entity) {
        super(entity);
        this.reaction = entity.getReaction();
    }

    public ReactionEnum getReaction() {
        return reaction;
    }

    public void setReaction(ReactionEnum reaction) {
        this.reaction = reaction;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
