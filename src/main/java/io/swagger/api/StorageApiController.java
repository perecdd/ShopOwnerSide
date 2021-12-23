package io.swagger.api;

import com.fasterxml.jackson.core.JsonParser;
import io.swagger.model.InlineResponse200;
import io.swagger.model.Product;
import io.swagger.model.StorageBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.models.auth.In;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
import java.util.LinkedList;
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

    public ResponseEntity<InlineResponse200> getCompany(@Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="CompanyID", required=false) Integer companyID,@Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="name", required=false) String name,@Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="minPrice", required=false) Integer minPrice,@Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="maxPrice", required=false) Integer maxPrice,@Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="count", required=false) Integer count,@Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="productID", required=false) Integer productID) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("SELECT name FROM sqlite_master WHERE type='table'");
                if(companyID != null) sb.append(" AND name = [" + companyID + "]");
                sb.append(";");

                DataBase.statement.get(0).execute(sb.toString());
                ResultSet rs = DataBase.statement.get(0).getResultSet();

                JSONObject result = new JSONObject();
                JSONArray products = new JSONArray();

                List<Integer> companyIDs = new LinkedList<>();

                while(rs.next()) {
                    companyIDs.add(rs.getInt("name"));
                }

                var iter = companyIDs.listIterator();
                while(iter.hasNext()){
                    Integer table_name = iter.next();

                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("SELECT * FROM [" + table_name + "]");
                    if(productID != null || count != null || minPrice != null || maxPrice != null || name != null) stringBuilder.append(" WHERE ");
                    if(productID != null) stringBuilder.append(" productID = " + productID + " AND");
                    if(count != null) stringBuilder.append(" count >= " + count + " AND");
                    if(minPrice != null) stringBuilder.append(" price >= " + minPrice + " AND");
                    if(maxPrice != null) stringBuilder.append(" price <= " + maxPrice + " AND");
                    if(name != null) stringBuilder.append(" name = '" + name + "' AND");
                    if(productID != null || count != null || minPrice != null || maxPrice != null || name != null) stringBuilder.delete(stringBuilder.length() - 3, stringBuilder.length());
                    stringBuilder.append(";");

                    DataBase.statement.get(0).execute(stringBuilder.toString());
                    ResultSet resultSet = DataBase.statement.get(0).getResultSet();

                    while(resultSet.next()) {
                        JSONObject product = new JSONObject();
                        product.put("name", rs.getString("name"));
                        product.put("count", rs.getString("count"));
                        product.put("description", rs.getString("description"));
                        product.put("price", rs.getString("price"));
                        product.put("productid", rs.getString("productid"));
                        product.put("companyid", table_name);
                        product.put("Photo", rs.getString("Photo"));
                        products.add(product);
                    }
                }

                result.put("products", products);

                return new ResponseEntity<InlineResponse200>(objectMapper.readValue(result.toString(), InlineResponse200.class), HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<InlineResponse200>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<InlineResponse200>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Void> postCompany(@Parameter(in = ParameterIn.HEADER, description = "CompanyID" ,required=true,schema=@Schema()) @RequestHeader(value="CompanyID", required=true) Integer companyID,@Parameter(in = ParameterIn.HEADER, description = "Password" ,required=true,schema=@Schema()) @RequestHeader(value="Password", required=true) String password,@Parameter(in = ParameterIn.HEADER, description = "serverAddress" ,required=true,schema=@Schema()) @RequestHeader(value="serverAddress", required=true) String serverAddress) {
        String accept = request.getHeader("Accept");
        try {
            DataBase.statement.get(1).execute("SELECT companyid,\n" +
                    "       password,\n" +
                    "       serverAddress FROM companies WHERE companyid == " + companyID + ";");
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
