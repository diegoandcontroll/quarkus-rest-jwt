package com.github.diegoandcontroll.services;

import java.util.HashSet;

import java.util.Set;

import com.github.diegoandcontroll.inputs.Auth.IAuthLogin;
import com.github.diegoandcontroll.inputs.Auth.IAuthResponse;
import com.github.diegoandcontroll.inputs.User.IUser;
import com.github.diegoandcontroll.inputs.User.IUserResponse;
import com.github.diegoandcontroll.model.User;
import com.github.diegoandcontroll.repositories.UserRepository;
import com.github.diegoandcontroll.utils.HashUtil;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class AuthService {

  @Inject
  UserRepository repository;

  @Inject
  TokenService tokenService;

  @Inject
  HashUtil hash;

  @Transactional
  public IUserResponse signUp(IUser IRequest) {
    String encryptPassword = hash.encryptPassword(IRequest.password());
    var newIuser = new IUser(IRequest.firstname(), IRequest.lastname(), IRequest.email(), encryptPassword);
    var user = new User(newIuser);
    Set<String> roles = new HashSet<>();
    roles.add("user");
    user.setRoles(roles);
    repository.persist(user);
    return IUserResponse.toIUserResponse(user);
  }

  public IAuthResponse signIn(IAuthLogin IRequest) {
    User user = repository.findByEmail(IRequest.email());
    boolean verifyPassword = hash.verifyPassword(IRequest.password(), user.getPassword());
    if (user != null && verifyPassword) {
      String token = tokenService.generate(user);
      IUserResponse toUserResponse = IUserResponse.toIUserResponse(user);
      IAuthResponse iAuthResponse = new IAuthResponse(toUserResponse, token);
      return iAuthResponse;
    }
    throw new NotFoundException("User not found!");
  }

}
