/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.29).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.InlineResponse2001;
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
public interface TicketCompanyApi {

    @Operation(summary = "Get company orders.", description = "Allows the company to receive all orders received from users by email and password.", tags={  })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = InlineResponse2001.class)))),

            @ApiResponse(responseCode = "400", description = "Bad Request") })
    @RequestMapping(value = "/ticketCompany",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<InlineResponse2001>> getTicket(@Parameter(in = ParameterIn.HEADER, description = "Company email for identification." ,required=true,schema=@Schema()) @RequestHeader(value="email", required=true) String email, @Parameter(in = ParameterIn.HEADER, description = "Company password" ,required=true,schema=@Schema()) @RequestHeader(value="password", required=true) String password);


    @Operation(summary = "Changes the order status.", description = "The request changes the status of the specified order from the company.", tags={  })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Bad Request") })
    @RequestMapping(value = "/ticketCompany",
            method = RequestMethod.POST)
    ResponseEntity<Void> ticketCompanyPost(@Parameter(in = ParameterIn.HEADER, description = "Company email for identification." ,required=true,schema=@Schema()) @RequestHeader(value="email", required=true) String email, @Parameter(in = ParameterIn.HEADER, description = "Company password" ,required=true,schema=@Schema()) @RequestHeader(value="password", required=true) String password, @Parameter(in = ParameterIn.HEADER, description = "New order status." ,required=true,schema=@Schema()) @RequestHeader(value="status", required=true) String status,@Parameter(in = ParameterIn.HEADER, description = "Order ID." ,required=true,schema=@Schema()) @RequestHeader(value="ticket", required=true) Integer ticket);

}

