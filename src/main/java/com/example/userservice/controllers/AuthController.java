package com.example.userservice.controllers;

import com.example.userservice.dto.LoginUser;
import com.example.userservice.dto.UserApplicationDTO;
import com.example.userservice.dto.UserEntityDTO;
import com.example.userservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(
            summary = "Authenticate user and generate JWT.",
            description = "Authenticates the user with the provided credentials and generates a JWT token.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User credentials for authentication.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginUser.class),
                            examples = @ExampleObject(
                                    name = "Login Example",
                                    summary = "Standard login credentials",
                                    description = "Example data for user authentication with username and password.",
                                    value = "{ \"username\": \"john\", \"password\": \"securepassword\" }"
                            )

                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "JWT token generated successfully.",
                    content = @Content(
                            mediaType = "text/plain",
                            schema = @Schema(type = "string"),
                            examples = @ExampleObject(
                                    name = "JWT Token Example",
                                    summary = "JWT Token",
                                    description = "Example JWT token returned upon successful authentication.",
                                    value = "{ \"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Invalid credentials.",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PostMapping("/login")
    private Mono<ResponseEntity<String>> login(@RequestBody LoginUser loginUser) {
        return authService.login(loginUser);
    }

    @Operation(
            summary = "Register",
            description = "Register with the data provided.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Registration data.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserEntityDTO.class),
                            examples =  @ExampleObject(
                                    name = "Registration example",
                                    summary = "Registration details",
                                    description = "Example data for create a new user.",
                                    value = "{ \"name\": \"John Smith\", \"username\": \"JohnSw\", \"email\": " + "\"john@example.com\", \"password\": " + "\"1234567890\"}"
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Successful registration",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserEntityDTO.class),
                            examples = @ExampleObject(
                                    name = "Registration Example",
                                    summary = "Registration",
                                    description = "Example of the response when a new user is successfully created.",
                                    value = "{ \"name\": \"John Smith\", \"username\": \"JohnSw\", \"email\": \"john@example.com\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Invalid input data",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserEntityDTO> createUser(@RequestBody Mono<UserApplicationDTO> userAppMono) {
        return authService.createUser(userAppMono);
    }

}
