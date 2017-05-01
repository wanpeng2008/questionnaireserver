package com.zjp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.annotation.Generated;
import javax.persistence.*;
import java.util.UUID;

/**
 * Created by 万鹏 on 2017/4/27.
 */
@Entity
@Table(name = "ROLE")
public class Role {
    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonIgnore
    private UUID id;
    @Column(name = "name",nullable=false,unique=true)
    private String name;

    public Role(String name) {
        this.name = name;
    }

    public Role() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
