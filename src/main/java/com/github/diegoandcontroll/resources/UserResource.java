package com.github.diegoandcontroll.resources;

import java.util.UUID;

import com.github.diegoandcontroll.inputs.User.IUser;
import com.github.diegoandcontroll.inputs.User.IUserResponse;
import com.github.diegoandcontroll.model.User;
import com.github.diegoandcontroll.services.UserService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService service;

   

    @GET
    public Response getAllItems() {
        return Response.status(Response.Status.OK).entity(service.getAllItems()).build();
    }

    @GET
    @Path("/{id}")
    public User getItemById(@PathParam("id") UUID id) {
        return service.getItemById(id);
    }

    @POST
    public Response createItem(IUser item) {
        IUserResponse newItem = service.createItem(item);
        return Response.status(Response.Status.CREATED).entity(newItem).build();
    }

    @PUT
    @Path("/{id}")
    public User updateItem(@PathParam("id") UUID id, IUser item) {
        return service.updateItem(item, id);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteItem(@PathParam("id") UUID id) {
        boolean deleted = service.deleteItem(id);
        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    
}
