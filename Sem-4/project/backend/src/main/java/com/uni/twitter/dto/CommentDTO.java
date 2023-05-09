package com.uni.twitter.dto;

import com.uni.twitter.entity.Comment;

public class CommentDTO extends AbstractDTO<Long> {

    private static final long serialVersionUID = -2762919556600912186L;

    private String text;
    private Long postId;

    public CommentDTO() {
    }

    public CommentDTO(Comment entity) {
        super(entity);
        this.text = entity.getText();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
