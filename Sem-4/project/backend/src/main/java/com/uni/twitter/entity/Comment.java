package com.uni.twitter.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "T_COMMENT")
public class Comment extends BaseEntity<Long> {

    private static final long serialVersionUID = 5747502468368452834L;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "T_COMMENT_GEN", sequenceName = "T_COMMENT_GEN_SQ", allocationSize = 1)
    @GeneratedValue(generator = "T_COMMENT_GEN", strategy = GenerationType.SEQUENCE)
    private Long Id;

    @NotBlank
    @NotNull
    @Column(name = "TEXT", nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false, foreignKey = @ForeignKey(name = "FK_T_POST_COMMENT"))
    private Post post;

    @Override
    public Long getId() {
        return this.Id;
    }

    @Override
    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Post getPostComment() {
        return post;
    }

    public void setPostComment(Post postComment) {
        this.post = postComment;
    }
}
