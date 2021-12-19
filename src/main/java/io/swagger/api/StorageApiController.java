package io.swagger.api;

import com.fasterxml.jackson.core.JsonParser;
import io.swagger.model.InlineResponse200;
import io.swagger.model.Product;
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
import javax.xml.crypto.Data;
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
                String queue = new String();
                if(companyID != -1) {
                    queue += "SELECT productid,\n" +
                            "       photo,\n" +
                            "       name,\n" +
                            "       count,\n" +
                            "       description,\n" +
                            "       price " +
                            "FROM [" + companyID + "]";
                }
                else{
                    // TODO: searching in all tables
                }

                boolean where = false;
                if(productID != null){
                    if(!where){
                        where = true;
                        queue += "WHERE ";
                    }
                    else{
                        queue += " AND ";
                    }

                    queue += "\nproductid == " + productID;
                }
                if(count != null){
                    if(!where){
                        where = true;
                        queue += "WHERE ";
                    }
                    else{
                        queue += " AND ";
                    }

                    queue += "\ncount >= " + count;
                }
                if(minPrice != null){
                    if(!where){
                        where = true;
                        queue += "WHERE ";
                    }
                    else{
                        queue += " AND ";
                    }

                    queue += "\nprice >= " + minPrice;
                }
                if(maxPrice != null){
                    if(!where){
                        where = true;
                        queue += "WHERE ";
                    }
                    else{
                        queue += " AND ";
                    }

                    queue += "\nprice <= " + maxPrice;
                }
                if(name != null){
                    if(!where){
                        where = true;
                        queue += "WHERE ";
                    }
                    else{
                        queue += " AND ";
                    }

                    queue += "\nname == " + name;
                }
                queue += ";";

                System.out.println(queue);
                DataBase.statement.get(0).execute(queue);
                ResultSet rs = DataBase.statement.get(0).getResultSet();
                String result = new String();

                result += "{\"products\": [\n";

                while (rs != null && !rs.isClosed()) {
                    result += "{\n";
                    result += "\"name\": \"" + rs.getString("name") + "\",\n";
                    result += "\"companyid\": " + companyID + ",\n";
                    result += "\"count\": " + rs.getString("count") + ",\n";
                    result += "\"description\": \"" + rs.getString("description") + "\",\n";
                    result += "\"price\": " + rs.getString("price") + ",\n";
                    result += "\"productid\": " + rs.getString("productid") + ",\n";
                    result += "\"Photo\": \"" + rs.getString("photo") + "\"\n";
                    result += "}\n";
                    if(rs.next()) {
                        result += ",";
                    }
                }
                result += "]\n}";
                return new ResponseEntity<InlineResponse200>(objectMapper.readValue(result, InlineResponse200.class), HttpStatus.OK);
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<InlineResponse200>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<InlineResponse200>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> postCompany(@Parameter(in = ParameterIn.HEADER, description = "CompanyID" ,required=true,schema=@Schema()) @RequestHeader(value="CompanyID", required=true) Integer companyID,@Parameter(in = ParameterIn.HEADER, description = "Password" ,required=true,schema=@Schema()) @RequestHeader(value="Password", required=true) String password,@Parameter(in = ParameterIn.HEADER, description = "serverAddress" ,required=true,schema=@Schema()) @RequestHeader(value="serverAddress", required=true) String serverAddress) {
        String accept = request.getHeader("Accept");
        try {
            DataBase.statement.get(1).execute("SELECT " + companyID + " FROM companies;");
            ResultSet rs = DataBase.statement.get(1).getResultSet();

            if(!rs.next()){
                DataBase.registerCompany(companyID, password, serverAddress);
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

    public ResponseEntity<Void> putCompany(@Parameter(in = ParameterIn.HEADER, description = "CompanyID" ,required=true,schema=@Schema()) @RequestHeader(value="CompanyID", required=true) Integer companyID,@Parameter(in = ParameterIn.HEADER, description = "Password" ,required=true,schema=@Schema()) @RequestHeader(value="Password", required=true) String password,@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody StorageBody body) {
        String accept = request.getHeader("Accept");

        try {
            DataBase.statement.get(1).execute("SELECT companyid,\n" +
                    "       password,\n" +
                    "       serverAddress\n" +
                    "  FROM companies WHERE companyid = " + companyID + ";");

            ResultSet rs = DataBase.statement.get(1).getResultSet();

            if (rs.getString("password").equals(password)) {
                List<Product> productList = body.getProducts();
                for(Product product : productList){
                    DataBase.replaceProduct(companyID, product);
                }
                return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
            }
            else{
                return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
