package com.uni.twitter.entity;

import com.uni.twitter.util.ReactionEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "T_REACTION")
public class Reaction extends BaseEntity<Long> {

    private static final long serialVersionUID = -8581319746187738371L;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "T_REACTION_GEN", sequenceName = "T_REACTION_GEN_SQ", allocationSize = 1)
    @GeneratedValue(generator = "T_REACTION_GEN", strategy = GenerationType.SEQUENCE)
    private Long Id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "REACTION", nullable = false)
    private ReactionEnum reaction;

    @NotNull
    @Column(name = "QUANTITY", nullable = false)
    private Long quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false, foreignKey = @ForeignKey(name = "FK_T_POST_REACTION"))
    private Post post;

    @Override
    public Long getId() {
        return this.Id;
    }

    @Override
    public void setId(Long Id) {
        this.Id = Id;
    }

    public ReactionEnum getReaction() {
        return reaction;
    }

    public void setReaction(ReactionEnum reaction) {
        this.reaction = reaction;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public void addOneReaction() {
        this.quantity = this.quantity + 1;
    }

    public Post getPostReaction() {
        return post;
    }

    public void setPostReaction(Post postReaction) {
        this.post = postReaction;
    }
}
