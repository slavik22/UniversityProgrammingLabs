package com.uni.twitter.controller;

import com.uni.twitter.dto.ReactionDTO;
import com.uni.twitter.service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = {"/reaction"}, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class ReactionController extends AbstractRESTController<ReactionDTO, Long, ReactionService> {

    public ReactionController(@Autowired final ReactionService service) {
        super(service);
    }
}
