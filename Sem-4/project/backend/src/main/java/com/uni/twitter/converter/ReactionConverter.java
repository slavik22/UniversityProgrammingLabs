package com.uni.twitter.converter;

import com.uni.twitter.dto.ReactionDTO;
import com.uni.twitter.entity.Reaction;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import static com.uni.twitter.util.ErrorUtils.*;

@Component
public class ReactionConverter extends AbstractConverter<ReactionDTO, Reaction> implements IConverter<Long> {

    @Override
    public ReactionDTO convertEntity(Reaction entity) {

        Assert.notNull(entity, buildErrorMessage(BASE_INITIALIZATION_ERROR, ENTITY, NULL_MESSAGE_ERROR));

        return new ReactionDTO(entity);
    }

    @Override
    public Reaction convertDTO(ReactionDTO dto) {

        Assert.notNull(dto, buildErrorMessage(BASE_INITIALIZATION_ERROR, DTO, NULL_MESSAGE_ERROR));

        Reaction reaction = new Reaction();

        convertDTO2BaseEntity(dto, reaction);
        reaction.setReaction(dto.getReaction());

        return reaction;
    }
}
