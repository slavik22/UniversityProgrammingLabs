package com.uni.twitter.service;


import com.uni.twitter.converter.PostConverter;
import com.uni.twitter.dao.PostRepository;
import com.uni.twitter.dto.PostDTO;
import com.uni.twitter.entity.Post;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService
        extends BaseCRUDServiceImpl<PostDTO, Post, Long>
        implements IBaseCRUDService<PostDTO, Long> {

    public PostService(PostRepository repository, PostConverter converter) {
        super(repository, converter);
    }

    public List<PostDTO> getAll() {
        return this.repository.findAll().stream().map(this.converter::convertEntity).collect(Collectors.toList());
    }
}
