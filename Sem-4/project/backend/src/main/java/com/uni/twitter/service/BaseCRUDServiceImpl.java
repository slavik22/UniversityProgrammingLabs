package com.uni.twitter.service;

import com.uni.twitter.converter.AbstractConverter;
import com.uni.twitter.dto.AbstractDTO;
import com.uni.twitter.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static com.uni.twitter.util.ErrorUtils.*;

public abstract class BaseCRUDServiceImpl<DTO extends AbstractDTO<ID>, E extends BaseEntity<ID>, ID> {

    protected final JpaRepository<E, ID> repository;
    protected final AbstractConverter<DTO, E> converter;

    public BaseCRUDServiceImpl(JpaRepository<E, ID> repository,
                               AbstractConverter<DTO, E> converter) {

        Assert.notNull(repository, buildErrorMessage(BASE_INITIALIZATION_ERROR, DAO, NULL_MESSAGE_ERROR));
        this.repository = repository;

        Assert.notNull(repository, buildErrorMessage(BASE_INITIALIZATION_ERROR, CONVERTER, NULL_MESSAGE_ERROR));
        this.converter = converter;
    }


    @Transactional(readOnly = true)
    public DTO getById(ID Id) {

        Assert.notNull(Id, buildErrorMessage(BASE_PARAMETER_ERROR, ID_PARAM, NULL_MESSAGE_ERROR));
        Optional<E> entity = repository.findById(Id);

        if (entity.isPresent()) {
            return converter.convertEntity(entity.get());
        } else {
            throw new EntityNotFoundException(
                    buildErrorMessageWithValue(BASE_DB_ERROR, ENTITY_NOT_FOUND_FOR_ID_ERROR, Id));
        }
    }

    @Transactional
    public void deleteById(ID Id) {

        Assert.notNull(Id, buildErrorMessage(BASE_PARAMETER_ERROR, ID_PARAM, NULL_MESSAGE_ERROR));

        try {
            repository.deleteById(Id);
        } catch (Exception e) {
            throw new EntityNotFoundException(
                    buildErrorMessageWithValue(BASE_DB_ERROR, ENTITY_NOT_FOUND_FOR_ID_ERROR, Id));
        }
    }

    @Transactional
    public DTO update(DTO dto) {

        Assert.notNull(dto, buildErrorMessage(BASE_PARAMETER_ERROR, DTO, NULL_MESSAGE_ERROR));
        Assert.notNull(dto.getID(), buildErrorMessage(BASE_PARAMETER_ERROR, ID_PARAM, NULL_MESSAGE_ERROR));

        E savedEntity = repository.saveAndFlush(converter.convertDTO(dto));
        return converter.convertEntity(savedEntity);
    }

    @Transactional
    public DTO create(DTO dto) {

        Assert.notNull(dto, buildErrorMessage(BASE_PARAMETER_ERROR, DTO, NULL_MESSAGE_ERROR));

        E savedEntity = repository.saveAndFlush(converter.convertDTO(dto));
        return converter.convertEntity(savedEntity);
    }
}
