package com.bozzaccio.twitterclone.controller;

import com.bozzaccio.twitterclone.service.IBaseCRUDService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import static com.bozzaccio.twitterclone.util.ErrorUtils.*;

public class AbstractRESTController<DTO, ID, S extends IBaseCRUDService<DTO, ID>>
        implements IController {

    protected final S service;

    public AbstractRESTController(S service) {
        Assert.notNull(service, buildErrorMessage(BASE_INITIALIZATION_ERROR, SERVICE, NULL_MESSAGE_ERROR));
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable ID id) {

        try {
            return ResponseEntity.ok(service.getById(id));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable ID id) {

        try {
            service.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody DTO dto) {

        try {
            return ResponseEntity.ok(service.create(dto));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody DTO dto) {

        try {
            return ResponseEntity.ok(service.create(dto));
        } catch (Exception e) {
            return handleException(e);
        }
    }
}
