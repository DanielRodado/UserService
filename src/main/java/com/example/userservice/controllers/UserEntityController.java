package com.example.userservice.controllers;

import com.example.userservice.dto.UserApplicationDTO;
import com.example.userservice.dto.UserEntityDTO;
import com.example.userservice.service.UserEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Operations", description = "Operations related to user management.")
public class UserEntityController {

    @Autowired
    private UserEntityService userEntityService;

    @Operation(
            summary = "Retrieve User by ID",
            description = "Fetches the User details for the given user ID.",
            parameters = @Parameter(
                    name = "id",
                    description = "The ID of the user to retrieve",
                    required = true,
                    example = "1"
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserEntityDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @GetMapping("/{id}")
    public Mono<UserEntityDTO> getUserById(@PathVariable Long id) {
        return userEntityService.getUserDTOById(id);
    }

    @Operation( summary = "Retrieve all users", description = "Fetches a set of all users.")
    @ApiResponse(
            responseCode = "200",
            description = "Users retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserEntityDTO.class))
    )
    @GetMapping
    public Flux<UserEntityDTO> getAllUsers() {
        return userEntityService.findAllUsersDTO();
    }

    @Operation(
            summary = "Update an existing user",
            description = "Updates the user identified by the given item ID with the data provided.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The data to update an user.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserApplicationDTO.class),
                            examples =  @ExampleObject(
                                    name = "User update example",
                                    summary = "User update details",
                                    description = "Example data for update an user.",
                                    value = "{ \"name\": \"John\", \"email\": \"john@example.com\", \"password\": \"1234567890\"}"
                            )
                    )
            ),
            parameters = @Parameter(
                    name = "id",
                    description = "The ID of the user to update.",
                    required = true,
                    example = "1"
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "User updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserEntityDTO.class),
                            examples = @ExampleObject(
                                    name = "User updated Example",
                                    summary = "Updated user",
                                    description = "Example of the response when a new user is successfully updated.",
                                    value = "{ \"id\": \"1\", \"name\": \"John\", \"email\": \"john@example.com\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Invalid input data",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PutMapping("/{id}")
    public Mono<UserEntityDTO> updateUser(@PathVariable Long id, @RequestBody UserApplicationDTO userApp) {
        return userEntityService.updateUser(userApp, id);
    }

    @Operation(
            summary = "Delete an existing user",
            description = "Delete the user identified by the given ID.",
            parameters = @Parameter(
                    name = "id",
                    description = "The ID of the user to delete.",
                    required = true,
                    example = "1"
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "User updated successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deleteUser(@PathVariable Long id) {
        return userEntityService.deleteUserById(id).then(Mono.just(ResponseEntity.noContent().build()));
    }

}
