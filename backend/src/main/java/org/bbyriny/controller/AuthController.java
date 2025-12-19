package org.bbyriny.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bbyriny.dto.LoginCredentials;
import org.bbyriny.dto.TokenResponse;
import org.bbyriny.exceptions.UserAlreadyExistsException;
import org.bbyriny.exceptions.UserNotFoundException;
import org.bbyriny.service.TokenService;
import org.bbyriny.service.UserService;

@Path("/auth")
public class AuthController {
    public AuthController() {
    }

    private TokenService tokenService;
    private UserService userService;

    @Inject
    public AuthController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login")
    public Response login(LoginCredentials credentials) {
        try {
            boolean authenticated = userService.login(credentials.getUsername(), credentials.getPassword());

            if (!authenticated) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Invalid password")
                        .build();
            }

            String token = tokenService.generateToken(credentials.getUsername());
            return Response.ok(new TokenResponse(token)).build();

        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Server Error: " + e.getMessage())
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/register")
    public Response register(LoginCredentials credentials) {
        try {
            userService.register(credentials.getUsername(), credentials.getPassword());
            return Response.status(Response.Status.CREATED).build();
        } catch (UserAlreadyExistsException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Server error")
                    .build();
        }
    }

}
