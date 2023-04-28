package com.bozzaccio.twitterclone.service;

import com.bozzaccio.twitterclone.converter.PostConverter;
import com.bozzaccio.twitterclone.dao.PostRepository;
import com.bozzaccio.twitterclone.dto.PostDTO;
import com.bozzaccio.twitterclone.entity.Post;
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
