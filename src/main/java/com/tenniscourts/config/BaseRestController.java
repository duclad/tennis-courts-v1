package com.tenniscourts.config;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class BaseRestController {

    protected URI locationByEntity(Long entityId) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path(
                "/{id}").buildAndExpand(entityId).toUri();
    }
}
