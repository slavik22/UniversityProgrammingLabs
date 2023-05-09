package com.uni.twitter.dto;


import com.uni.twitter.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public abstract class AbstractDTO<ID> implements Serializable {

    private static final long serialVersionUID = 4070299567166356032L;

    private ID ID;
    private Date creationTimestamp;
    private String creationUser;
    private Date updateTimestamp;
    private String updateUser;
    private Long version;

    public AbstractDTO(BaseEntity<ID> entity) {
        this.ID = entity.getId();
        this.creationTimestamp = entity.getCreationTimestamp();
        this.creationUser = entity.getCreationUser();
        this.updateTimestamp = entity.getUpdateTimestamp();
        this.updateUser = entity.getUpdateUser();
        this.version = entity.getVersion();
    }

    public AbstractDTO() {
    }

    public ID getID() {
        return ID;
    }

    public void setID(ID ID) {
        this.ID = ID;
    }

    public Date getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Date creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public String getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
    }

    public Date getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Date updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
