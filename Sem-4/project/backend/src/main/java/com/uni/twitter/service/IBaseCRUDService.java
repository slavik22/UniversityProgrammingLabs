package com.uni.twitter.service;

public interface IBaseCRUDService<DTO, ID> {

    DTO getById(ID Id);

    DTO create(DTO dto);

    DTO update(DTO dto);

    void deleteById(ID Id);

}
