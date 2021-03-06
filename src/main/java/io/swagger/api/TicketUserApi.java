/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.29).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.InlineResponse2001;
import io.swagger.model.InlineResponse2002;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CookieValue;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-24T11:40:06.895Z[GMT]")
@Validated
public interface TicketUserApi {

    @Operation(summary = "Receives all user orders.", description = "The request receives for the user all the orders that he has made.", tags={  })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = InlineResponse2002.class)))),

            @ApiResponse(responseCode = "400", description = "Bad Request") })
    @RequestMapping(value = "/ticketUser",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<InlineResponse2002>> ticketUserGet(@Parameter(in = ParameterIn.HEADER, description = "User's email" ,required=true,schema=@Schema()) @RequestHeader(value="email", required=true) String email, @Parameter(in = ParameterIn.HEADER, description = "User's password" ,required=true,schema=@Schema()) @RequestHeader(value="password", required=true) String password);


    @Operation(summary = "Cancels the user's order.", description = "The request allows the user to set the order status to \"cancel\", thereby canceling it.", tags={  })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),

            @ApiResponse(responseCode = "400", description = "Bad Request") })
    @RequestMapping(value = "/ticketUser",
            method = RequestMethod.POST)
    ResponseEntity<Void> ticketUserPost(@Parameter(in = ParameterIn.HEADER, description = "User's email" ,required=true,schema=@Schema()) @RequestHeader(value="email", required=true) String email, @Parameter(in = ParameterIn.HEADER, description = "User's password" ,required=true,schema=@Schema()) @RequestHeader(value="password", required=true) String password, @Parameter(in = ParameterIn.HEADER, description = "Order ID." ,required=true,schema=@Schema()) @RequestHeader(value="ticket", required=true) Integer ticket);

}

