package com.github.diegoandcontroll.resources;

import com.github.diegoandcontroll.inputs.Auth.IAuthLogin;
import com.github.diegoandcontroll.inputs.User.IUser;
import com.github.diegoandcontroll.services.AuthService;
import com.github.diegoandcontroll.services.TokenService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
  @Inject
  TokenService tokenService;
  @Inject
  AuthService service;

  @POST
  @Path("/signup")
  public Response signUp(IUser IRequest) {
    return Response.status(Response.Status.CREATED).entity(service.signUp(IRequest)).build();
  }

  @POST
  @Path("/signin")
  public Response signIn(IAuthLogin IRequest) {
    return Response.status(Response.Status.OK).entity(service.signIn(IRequest)).build();
  }
}
