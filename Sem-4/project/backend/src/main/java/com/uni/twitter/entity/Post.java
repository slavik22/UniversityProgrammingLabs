package com.uni.twitter.entity;


import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "T_POST")
public class Post extends BaseEntity<Long> {

    private static final long serialVersionUID = -961106512837249525L;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "T_POST_GEN", sequenceName = "T_POST_GEN_SQ", allocationSize = 1)
    @GeneratedValue(generator = "TT_POST_GEN", strategy = GenerationType.SEQUENCE)
    private Long Id;

    @NotNull
    @Column(name = "NAME", nullable = false)
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "post",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    @Column(name = "COMMENT_COUNTER")
    private Long commentCounter;

    @OneToMany(mappedBy = "post",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Reaction> reactions = new ArrayList<>();

    @Override
    public Long getId() {
        return this.Id;
    }

    @Override
    public void setId(Long Id) {
        this.Id = Id;
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

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public List<Reaction> getReactions() {
        return reactions;
    }

    public void setReactions(List<Reaction> reactions) {
        this.reactions = reactions;
    }

    public Long getCommentCounter() {
        return commentCounter;
    }

    public void setCommentCounter(Long commentCounter) {
        this.commentCounter = commentCounter;
    }

    public void increaseCommentCounter() {

        if (commentCounter == null) {
            this.commentCounter = 1L;
        } else {
            this.commentCounter = this.commentCounter + 1L;
        }
    }
}
