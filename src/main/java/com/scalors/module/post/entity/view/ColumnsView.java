package com.scalors.module.post.entity.view;

import com.scalors.module.post.controller.PostController;
import com.scalors.module.post.entity.Post;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

@ManagedBean(name="dtColumnsView")
@ViewScoped
public class ColumnsView implements Serializable {
    private List<Post> posts;

    @Inject
    private PostController postController;

    @PostConstruct
    public void init() {
        posts = postController.findAllPosts();
    }

    public List<Post> getPosts() {
        return posts;
    }
}
