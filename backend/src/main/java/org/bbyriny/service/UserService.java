package org.bbyriny.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.bbyriny.exceptions.UserAlreadyExistsException;
import org.bbyriny.exceptions.UserNotFoundException;
import org.bbyriny.models.Users;
import org.bbyriny.repository.UserRepository;
import org.bbyriny.util.PasswordHasher;


@ApplicationScoped
public class UserService {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public UserService() {
        this.userRepository = null;
        this.passwordHasher = null;
    }

    @Inject
    public UserService(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    public boolean login(String username, String password) {
        var userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(
                    Response.Status.BAD_REQUEST,
                    "User not found"
            );
        }
        Users user = userOptional.get();
        return passwordHasher.verifyPassword(password, user.getPassword());
    }


    public boolean register(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException(
                    Response.Status.CONFLICT,
                    "User already exists"
            );
        }

        String hashed = passwordHasher.hashPassword(password);
        userRepository.save(new Users(username, hashed));
        return true;
    }

}
