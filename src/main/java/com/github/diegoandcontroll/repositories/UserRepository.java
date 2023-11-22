package com.github.diegoandcontroll.repositories;

import java.util.UUID;

import com.github.diegoandcontroll.model.User;


import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<User,UUID> {
}
