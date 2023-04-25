package com.uni.twitter.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "T_USER",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User extends BaseEntity<Long> {

    private static final long serialVersionUID = 8021536115646875239L;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "T_USER_GEN", sequenceName = "T_USER_GEN_SQ", allocationSize = 1)
    @GeneratedValue(generator = "T_USER_GEN", strategy = GenerationType.SEQUENCE)
    private Long Id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @Override
    public Long getId() {
        return this.Id;
    }

    @Override
    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
