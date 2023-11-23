package com.github.diegoandcontroll.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import java.util.stream.Collectors;

import com.github.diegoandcontroll.inputs.Auth.IAuthResponse;
import com.github.diegoandcontroll.inputs.User.IUser;
import com.github.diegoandcontroll.inputs.User.IUserResponse;
import com.github.diegoandcontroll.model.User;
import com.github.diegoandcontroll.repositories.UserRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository repository;

    @Inject
    TokenService tokenService;

    public List<IUserResponse> getAllItems() {
        List<User> listAll = repository.listAll();

        return listAll.stream().map(i -> IUserResponse.toIUserResponse(i)).collect(Collectors.toList());
    }

    public User getItemById(UUID id) {
        return repository.findById(id);
    }

    @Transactional
    public IUserResponse createItem(IUser item) {
        var user = new User(item);
        Set<String> roles = new HashSet<>();
        roles.add("user");
        user.setRoles(roles);
        repository.persist(user);
        var response = IUserResponse.toIUserResponse(user);
        return response;
    }

    @Transactional
    public User updateItem(IUser newItem, UUID id) {
        User existingItem = repository.findById(id);
        if (existingItem != null) {
            existingItem.setFirstName(newItem.firstname());
            existingItem.setLastName(newItem.lastname());
            existingItem.setEmail(newItem.email());
            existingItem.setPassword(newItem.password());
            repository.persist(existingItem);
        }
        return existingItem;
    }

    @Transactional
    public boolean deleteItem(UUID id) {
        return repository.deleteById(id);
    }

    public IAuthResponse getToken(UUID userId) {
        Optional<User> isUser = repository.findByIdOptional(userId);
        if (isUser.isPresent()) {
            User user = isUser.get();
            String token = tokenService.generate(user);
            IUserResponse toUserResponse = IUserResponse.toIUserResponse(user);
            IAuthResponse iAuthResponse = new IAuthResponse(toUserResponse, token);
            return iAuthResponse;
        }
        throw new NotFoundException("User not found!");
    }
}
