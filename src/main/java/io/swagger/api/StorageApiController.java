package io.swagger.api;

import io.swagger.model.InlineResponse200;
import io.swagger.model.StorageBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-19T09:20:14.611Z[GMT]")
@RestController
public class StorageApiController implements StorageApi {

    private static final Logger log = LoggerFactory.getLogger(StorageApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public StorageApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<InlineResponse200> getCompany(@Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="CompanyID", required=false) Integer companyID,@Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="name", required=false) String name,@Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="minPrice", required=false) String minPrice,@Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="maxPrice", required=false) String maxPrice,@Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="count", required=false) String count,@Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="productID", required=false) String productID) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<InlineResponse200>(objectMapper.readValue("{\n  \"products\" : [ {\n    \"companyid\" : 0.8008281904610115,\n    \"productid\" : 6.027456183070403,\n    \"price\" : 1.4658129805029452,\n    \"name\" : \"name\",\n    \"count\" : 5.962133916683182,\n    \"description\" : \"description\",\n    \"Photo\" : \"Photo\"\n  }, {\n    \"companyid\" : 0.8008281904610115,\n    \"productid\" : 6.027456183070403,\n    \"price\" : 1.4658129805029452,\n    \"name\" : \"name\",\n    \"count\" : 5.962133916683182,\n    \"description\" : \"description\",\n    \"Photo\" : \"Photo\"\n  } ]\n}", InlineResponse200.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<InlineResponse200>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<InlineResponse200>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> postCompany(@Parameter(in = ParameterIn.HEADER, description = "CompanyID" ,required=true,schema=@Schema()) @RequestHeader(value="CompanyID", required=true) Integer companyID,@Parameter(in = ParameterIn.HEADER, description = "Password" ,required=true,schema=@Schema()) @RequestHeader(value="Password", required=true) String password,@Parameter(in = ParameterIn.HEADER, description = "serverAddress" ,required=true,schema=@Schema()) @RequestHeader(value="serverAddress", required=true) String serverAddress) {
        String accept = request.getHeader("Accept");
        try {
            System.out.println("0");
            DataBase.statement.get(1).execute("SELECT " + companyID + " from sqlite_master where type= \"table\";");
            System.out.println("1");
            ResultSet rs = DataBase.statement.get(1).getResultSet();
            System.out.println("2");

            if(rs.next()){
                System.out.println("3");
                DataBase.statement.get(1).execute("INSERT INTO companies (\n" +
                        "                          password,\n" +
                        "                          serverAddress\n" +
                        "                      )\n" +
                        "                      VALUES (\n" +
                        "                          '" + password + "',\n" +
                        "                          '" + serverAddress + "'\n" +
                        "                      );\n");

                System.out.println("4");
                DataBase.addCompany(companyID);
                System.out.println("5");
                return new ResponseEntity<Void>(HttpStatus.CREATED);
            }
            else{
                return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> putCompany(@Parameter(in = ParameterIn.HEADER, description = "Company ID" ,required=true,schema=@Schema()) @RequestHeader(value="Company ID", required=true) Integer companyID,@Parameter(in = ParameterIn.HEADER, description = "Password" ,required=true,schema=@Schema()) @RequestHeader(value="Password", required=true) String password,@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody StorageBody body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
