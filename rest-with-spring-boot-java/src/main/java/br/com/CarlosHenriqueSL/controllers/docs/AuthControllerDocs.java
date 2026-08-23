package br.com.CarlosHenriqueSL.controllers.docs;

import br.com.CarlosHenriqueSL.data.dto.secutiry.AccountCredentialsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface AuthControllerDocs {

    @Operation(summary = "Authenticates an user and returns a token",
            description = "Validates user credentials and generates an access token for authentication",
            tags = {"Authentication"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<?> signIn(@RequestBody AccountCredentialsDTO credentials);

    @Operation(summary = "Refreshes a Token for a authenticated user",
            description = "Generates a new access token using the provided refresh token and username",
            tags = {"Authentication"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<?> refreshToken(@PathVariable(value = "username") String username,
                                          @RequestHeader(value = "Authorization") String refreshToken);

    @Operation(summary = "Creates a new User",
            description = "Creates a new User receiving a Username, Password and Fullname JSON body",
            tags = {"User Management"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public AccountCredentialsDTO create(@RequestBody AccountCredentialsDTO credentials);
}
