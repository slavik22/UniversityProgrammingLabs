package com.uni.twitter.dao;

import com.uni.twitter.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT COUNT(c) FROM Comment c JOIN Post p WHERE p.id =:id")
    Long countCommentByPostId(@Param("id") Long postId);
}
