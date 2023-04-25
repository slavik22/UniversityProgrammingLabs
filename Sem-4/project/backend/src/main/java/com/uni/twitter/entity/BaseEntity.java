package com.uni.twitter.entity;

import com.uni.twitter.security.UserLogged;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;

@MappedSuperclass
public abstract class BaseEntity<ID> extends Auditable implements Serializable {

    private static final long serialVersionUID = -8854348232451333086L;

    public abstract ID getId();

    public abstract void setId(ID Id);

    @PrePersist
    public void prePersist() {
        String createdByUser = getUsernameOfAuthenticatedUser();
        this.setCreationUser(createdByUser);
        this.setUpdateUser(createdByUser);
    }

    @PreUpdate
    public void preUpdate() {
        this.setUpdateUser(getUsernameOfAuthenticatedUser());
    }

    private String getUsernameOfAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser")) {
            return null;
        }

        return ((UserLogged) authentication.getPrincipal()).getUsername();
    }
}
