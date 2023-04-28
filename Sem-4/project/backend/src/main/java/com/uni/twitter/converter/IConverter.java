package com.bozzaccio.twitterclone.converter;

import com.bozzaccio.twitterclone.dto.AbstractDTO;
import com.bozzaccio.twitterclone.entity.BaseEntity;

import java.util.Objects;

public interface IConverter<ID> {

    default void convertDTO2BaseEntity(AbstractDTO<ID> dto, BaseEntity<ID> entity) {

        if (Objects.nonNull(dto.getID()))
            entity.setId(dto.getID());

        entity.setCreationTimestamp(dto.getCreationTimestamp());
        entity.setCreationUser(dto.getCreationUser());
        entity.setUpdateTimestamp(dto.getUpdateTimestamp());
        entity.setUpdateUser(dto.getUpdateUser());
        entity.setVersion(dto.getVersion());
    }
}
