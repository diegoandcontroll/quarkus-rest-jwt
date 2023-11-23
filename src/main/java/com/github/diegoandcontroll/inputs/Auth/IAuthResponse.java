package com.github.diegoandcontroll.inputs.Auth;

import com.github.diegoandcontroll.inputs.User.IUserResponse;

public record IAuthResponse(IUserResponse user, String token) {
  
}
