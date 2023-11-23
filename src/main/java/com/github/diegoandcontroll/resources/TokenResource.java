package com.github.diegoandcontroll.resources;

import java.util.UUID;

import com.github.diegoandcontroll.inputs.Auth.GetToken;
import com.github.diegoandcontroll.inputs.Auth.IAuthResponse;
import com.github.diegoandcontroll.services.TokenService;
import com.github.diegoandcontroll.services.UserService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/token")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TokenResource {
  @Inject
  TokenService tokenService;
  @Inject
  UserService service;

  @GET
  public Response getToken() {

    return Response.status(Response.Status.OK).entity("Hellow Token").build();
  }

  @POST
  public Response getToken(GetToken obj) {
    IAuthResponse response = service.getToken(obj.id());

    return Response.status(Response.Status.OK).entity(response).build();
  }
}
