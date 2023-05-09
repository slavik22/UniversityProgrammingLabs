package com.uni.twitter.service;

import com.uni.twitter.converter.CommentConverter;
import com.uni.twitter.dao.CommentRepository;
import com.uni.twitter.dao.PostRepository;
import com.uni.twitter.dto.CommentDTO;
import com.uni.twitter.entity.Comment;
import com.uni.twitter.entity.Post;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.uni.twitter.util.ErrorUtils.*;

@Service
public class CommentService
        extends BaseCRUDServiceImpl<CommentDTO, Comment, Long>
        implements IBaseCRUDService<CommentDTO, Long> {

    private final PostRepository postRepository;

    public CommentService(CommentRepository repository,
                          CommentConverter converter,
                          PostRepository postRepository) {
        super(repository, converter);
        this.postRepository = postRepository;
    }

    @Override
    public CommentDTO update(CommentDTO dto) {
        Assert.notNull(dto.getPostId(), buildErrorMessage(BASE_PARAMETER_ERROR, ID_PARAM, NULL_MESSAGE_ERROR));
        return super.update(dto);
    }

    @Override
    public CommentDTO create(CommentDTO dto) {

        Assert.notNull(dto, buildErrorMessage(BASE_PARAMETER_ERROR, DTO, NULL_MESSAGE_ERROR));
        Assert.notNull(dto.getPostId(), buildErrorMessage(BASE_PARAMETER_ERROR, ID_PARAM, NULL_MESSAGE_ERROR));

        Optional<Post> postOptional = postRepository.findById(dto.getPostId());

        if (postOptional.isPresent()) {

            Post post = postOptional.get();

            Comment comment = converter.convertDTO(dto);
            post.increaseCommentCounter();
            comment.setPostComment(post);

            post.getComments().add(comment);

            postRepository.saveAndFlush(post);

            return dto;
        } else {
            throw new EntityNotFoundException(buildErrorMessageWithValue(BASE_DB_ERROR,
                    ENTITY_NOT_FOUND_FOR_ID_ERROR,
                    dto.getPostId()));
        }
    }

    public List<CommentDTO> getCommentsByPostId(Long postId) {

        Assert.notNull(postId, buildErrorMessage(BASE_PARAMETER_ERROR, ID_PARAM, NULL_MESSAGE_ERROR));

        Optional<Post> postOptional = postRepository.findById(postId);

        return postOptional.map(post -> post.getComments().stream()
                .map(this.converter::convertEntity)
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }
}
