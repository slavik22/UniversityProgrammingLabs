package com.bozzaccio.twitterclone.converter;

import com.bozzaccio.twitterclone.dto.CommentDTO;
import com.bozzaccio.twitterclone.entity.Comment;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import static com.bozzaccio.twitterclone.util.ErrorUtils.*;

@Component
public class CommentConverter extends AbstractConverter<CommentDTO, Comment> implements IConverter<Long> {

    @Override
    public CommentDTO convertEntity(Comment entity) {

        Assert.notNull(entity, buildErrorMessage(BASE_INITIALIZATION_ERROR, ENTITY, NULL_MESSAGE_ERROR));

        return new CommentDTO(entity);
    }

    @Override
    public Comment convertDTO(CommentDTO dto) {

        Assert.notNull(dto, buildErrorMessage(BASE_INITIALIZATION_ERROR, DTO, NULL_MESSAGE_ERROR));

        Comment comment = new Comment();

        convertDTO2BaseEntity(dto, comment);
        comment.setText(dto.getText());

        return comment;
    }
}
