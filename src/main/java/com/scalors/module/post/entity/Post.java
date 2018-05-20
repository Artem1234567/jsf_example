package com.scalors.module.post.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "post")
@ManagedBean(name = "postBean", eager = true)
@RequestScoped
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    @Column(name = "dataType")
    private String dataType;

    @Column(name = "creationDateTime")
    private Date creationDateTime;

    @Column(name = "lastUpdateDateTime")
    @JsonIgnore
    private Date lastUpdateDateTime;

    @Column(name = "countUpdates")
    @JsonIgnore
    private long countUpdates;

}
