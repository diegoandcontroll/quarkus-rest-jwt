package com.github.diegoandcontroll.inputs.User;

import java.util.UUID;

import com.github.diegoandcontroll.model.User;

public record IUserResponse(UUID id, String firstName, String lastName, String email) {
  public static IUserResponse toIUserResponse(User userEntity) {
    var response = new IUserResponse(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getEmail());
    return response;
  }
}