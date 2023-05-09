package com.uni.twitter.converter;


public abstract class AbstractConverter<DTO, E> {

    public abstract DTO convertEntity(E entity);

    public abstract E convertDTO(DTO dto);
}
