package com.scalors.module.post.entity;

import com.scalors.module.post.controller.PostController;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;

@ManagedBean(name = "postData", eager = true)
@SessionScoped
public class PostData implements Serializable {
    @Inject
    private PostController postController;

    public void saveAction(Post post) throws IOException {
        postController.savePost(post);

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect(externalContext.getRequestContextPath() + "/index.xhtml");
    }
}
