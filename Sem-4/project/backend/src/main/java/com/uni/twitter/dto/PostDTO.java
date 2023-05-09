package com.uni.twitter.dto;

import com.uni.twitter.entity.BaseEntity;
import com.uni.twitter.entity.Post;
import com.uni.twitter.util.ReactionEnum;

import java.util.HashMap;
import java.util.Map;

public class PostDTO extends AbstractDTO<Long> {

    private static final long serialVersionUID = 8701496414601633465L;

    private String title;
    private String description;
    private Long totalComment;
    private Map<ReactionEnum, Long> reactionMap;

    public PostDTO() {
    }

    public PostDTO(BaseEntity<Long> entity) {
        super(entity);
    }

    public PostDTO(Post entity) {
        super(entity);
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.totalComment = entity.getCommentCounter();
        this.reactionMap = new HashMap<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(Long totalComment) {
        this.totalComment = totalComment;
    }

    public Map<ReactionEnum, Long> getReactionMap() {
        return reactionMap;
    }

    public void setReactionMap(Map<ReactionEnum, Long> reactionMap) {
        this.reactionMap = reactionMap;
    }
}
