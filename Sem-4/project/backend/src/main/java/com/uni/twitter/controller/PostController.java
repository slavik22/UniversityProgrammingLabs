package com.uni.twitter.controller;

import com.uni.twitter.dto.PostDTO;
import com.uni.twitter.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = {"/post"}, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class PostController extends AbstractRESTController<PostDTO, Long, PostService> {

    public PostController(@Autowired final PostService service) {
        super(service);
    }

    @GetMapping
    public ResponseEntity<?> getAllPosts() {

        try {
            return ResponseEntity.ok(service.getAll());
        } catch (Exception e) {
            return handleException(e);
        }
    }
}
