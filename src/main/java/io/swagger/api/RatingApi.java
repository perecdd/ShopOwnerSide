package io.swagger.api;

import io.swagger.model.Rating;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-19T09:20:14.611Z[GMT]")
@Validated
public interface RatingApi {
    @Operation(summary = "Adds a score to the company's rating.", description = "The request allows the user to evaluate the quality of the company's work by adding their own rating.", tags={  })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Bad Request") })
    @RequestMapping(value = "/rate",
            method = RequestMethod.POST)
    ResponseEntity<Void> rateCompany(@Parameter(in = ParameterIn.HEADER, description = "The user's email address for identification." ,required=true,schema=@Schema()) @RequestHeader(value="email", required=true) String email, @Parameter(in = ParameterIn.HEADER, description = "User password." ,required=true,schema=@Schema()) @RequestHeader(value="Password", required=true) String password, @Parameter(in = ParameterIn.HEADER, description = "User rating." ,required=true,schema=@Schema()) @RequestHeader(value="rating", required=true) Integer rating, @Parameter(in = ParameterIn.HEADER, description = "Company ID in the database." ,required=true,schema=@Schema()) @RequestHeader(value="companyid", required=true) Integer companyid);

    @Operation(summary = "Gets the company's rating.", description = "Allows a user or a company to get a company rating. The user can get it by requesting the company ID, while the company can request its rating using an email address.", tags={  })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Rating.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request") })
    @RequestMapping(value = "/rating",
            method = RequestMethod.GET)
    ResponseEntity<Rating> getRating(@Parameter(in = ParameterIn.HEADER, description = "Company ID in the database." ,required=false,schema=@Schema()) @RequestHeader(value="companyid", required=false) Integer companyid, @Parameter(in = ParameterIn.HEADER, description = "The email address for identifying the company in the database." ,required=false,schema=@Schema()) @RequestHeader(value="email", required=false) String email);
}
