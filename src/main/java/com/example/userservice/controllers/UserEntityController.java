package com.example.userservice.controllers;

import com.example.userservice.dto.UserEntityDTO;
import com.example.userservice.service.UserEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.example.userservice.utils.HeaderUtil.extractUsername;

@RestController
@RequestMapping("/api/users/current")
@Tag(name = "User Operations", description = "Operations related to user management.")
public class UserEntityController {

    @Autowired
    private UserEntityService userEntityService;

    @Operation(
            summary = "Get authenticated user information",
            description = "Retrieves the user entity associated with the currently authenticated user.",
            parameters = @Parameter(hidden = true)
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the authenticated user.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserEntityDTO.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied.",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @GetMapping
    public Mono<UserEntityDTO> getCurrentUser(ServerWebExchange serverWebExchange) {
        String username = extractUsername(serverWebExchange);
        return userEntityService.getUserDTOByUsername(username);
    }

}
