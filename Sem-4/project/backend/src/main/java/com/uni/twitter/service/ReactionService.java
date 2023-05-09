package com.uni.twitter.service;


import com.uni.twitter.converter.ReactionConverter;
import com.uni.twitter.dao.PostRepository;
import com.uni.twitter.dao.ReactionRepository;
import com.uni.twitter.dto.ReactionDTO;
import com.uni.twitter.entity.Post;
import com.uni.twitter.entity.Reaction;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static com.uni.twitter.util.ErrorUtils.*;

@Service
public class ReactionService
        extends BaseCRUDServiceImpl<ReactionDTO, Reaction, Long>
        implements IBaseCRUDService<ReactionDTO, Long> {

    private final PostRepository postRepository;

    public ReactionService(ReactionRepository repository,
                           ReactionConverter converter,
                           PostRepository postRepository) {
        super(repository, converter);
        this.postRepository = postRepository;
    }

    @Override
    public ReactionDTO create(ReactionDTO dto) {

        Assert.notNull(dto, buildErrorMessage(BASE_PARAMETER_ERROR, DTO, NULL_MESSAGE_ERROR));
        Assert.notNull(dto.getPostId(), buildErrorMessage(BASE_PARAMETER_ERROR, ID_PARAM, NULL_MESSAGE_ERROR));

        Optional<Post> postOptional = postRepository.findById(dto.getPostId());

        if (postOptional.isPresent()) {

            Post post = postOptional.get();

            Optional<Reaction> reactionOptional = post.getReactions().stream()
                    .filter(reaction -> reaction.getReaction().equals(dto.getReaction())).findFirst();

            if (reactionOptional.isPresent()) {
                reactionOptional.get().addOneReaction();
            } else {
                Reaction reactionToAdd = new Reaction();
                reactionToAdd.setReaction(dto.getReaction());
                reactionToAdd.setQuantity(1L);
                reactionToAdd.setPostReaction(post);
                post.getReactions().add(reactionToAdd);
            }

            postRepository.saveAndFlush(post);

            return dto;
        } else {
            throw new EntityNotFoundException(buildErrorMessageWithValue(BASE_DB_ERROR,
                    ENTITY_NOT_FOUND_FOR_ID_ERROR,
                    dto.getPostId()));
        }
    }
}
