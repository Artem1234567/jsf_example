package com.scalors.module.post.controller;

import com.scalors.module.post.repository.PostRepository;
import com.scalors.module.post.entity.Post;

import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("/post")
@SessionScoped
public class PostController {

    @Inject
    private PostRepository postRepository;

    @GET
    @Path("/find/id/{id : \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readById(@PathParam("id") long id) {
        Optional<Post> found = postRepository.findById(id);
        if (found.isPresent()) {
            return Response.status(Response.Status.OK).entity(found.get()).build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).entity("Post is null. Unable to Find").build();
    }

    @GET
    @Path("/find/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readByName(@PathParam("name") String name) {
        Optional<List<Post>> found = postRepository.findByName(name);
        if (found.isPresent()) {
            return Response.status(Response.Status.OK).entity(found.get()).build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).entity("Post is null. Unable to Find").build();
    }

    @GET
    @Path("/find/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Post> findAllPosts() {
        return (List<Post>) postRepository.findAll();
    }

    @POST
    @Path("/create/new")
    public Response savePost(Post post) {
        if (post == null) {
            return Response.status(Response.Status.NOT_MODIFIED).entity("Post is null. Unable to Create").build();
        }
        post.setCreationDateTime(new Date());
        postRepository.save(post);
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/create/many")
    public void createBatch(List<Post> list) {
        postRepository.savePostBatch(list);
    }

    @POST
    @Path("/update/name/{name}/{datatype}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateByName(@PathParam("name") String name, @PathParam("datatype") String datatype) {
        Optional<List<Post>> found = postRepository.findByName(name);
        if (found.isPresent()) {
            if (!found.get().isEmpty()) {
                Post post = found.get().get(0);
                post.setDataType(datatype);
                post.setLastUpdateDateTime(new Date());
                post.incrementCountUpdates();

                Optional<Post> updated = postRepository.update(post);
                if (updated.isPresent()) {
                    return Response.status(Response.Status.OK).entity("Post was successfully updated.").build();
                }
            }
        }
        return Response.status(Response.Status.OK).entity("Post was not found and was not updated.").build();
    }

    @POST
    @Path("/remove/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("id") Long id) {
        if (id == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Post id is null. Unable to Delete.").build();
        }
        int count = postRepository.removeById(id);
        if (count == 0) {
            return Response.status(Response.Status.NOT_MODIFIED).entity("Post was not found and was not deleted.").build();
        }
        return Response.status(Response.Status.OK).entity("Post was successfully deleted.").build();
    }

    @POST
    @Path("/remove/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteByName(@PathParam("name") String name) {
        if (name == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Post name is null. Unable to Delete.").build();
        }
        int count = postRepository.removeByName(name);
        if (count == 0) {
            return Response.status(Response.Status.NOT_MODIFIED).entity("Post was not found and was not deleted.").build();
        }
        return Response.status(Response.Status.OK).entity("Post was successfully deleted.").build();
    }

    @GET
    @Path("/info/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response creationDateTime(String name) {
        Optional<List<Post>> found = postRepository.findByName(name);
        if (found.isPresent()) {
            List<Date> dates = new ArrayList<>();
            for (Post p : found.get()) dates.add(p.getCreationDateTime());

            return Response.status(Response.Status.OK).entity(dates).build();
        }
        return Response.status(Response.Status.OK).entity("Post was not found.").build();
    }
}
