package io.swagger.api;

import io.swagger.model.InlineResponse200;
import io.swagger.model.Product;
import io.swagger.model.StorageBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

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
        System.out.println("getProducts");
        if (accept != null && accept.contains("application/json")) {
            try {
                StringBuilder sb = new StringBuilder();

                sb.append("SELECT table_name FROM information_schema.tables WHERE table_schema NOT IN ('information_schema','pg_catalog');");

                DataBase.statement.execute(sb.toString());
                ResultSet rs = DataBase.statement.getResultSet();

                JSONObject result = new JSONObject();
                JSONArray products = new JSONArray();

                List<Integer> companyIDs = new LinkedList<>();

                while(rs.next()) {
                    if(!rs.getString("table_name").equals("companies")
                            && !rs.getString("table_name").equals("tickets")
                            && !rs.getString("table_name").equals("users")
                    ) {
                        Integer table_name = rs.getInt("table_name");
                        if((companyID != null && table_name == companyID) || companyID == null) {
                            companyIDs.add(table_name);
                        }
                    }
                }

                var iter = companyIDs.listIterator();
                while(iter.hasNext()){
                    Integer table_name = iter.next();

                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("SELECT * FROM \"" + table_name + "\"");
                    if(productID != null || count != null || minPrice != null || maxPrice != null || name != null) stringBuilder.append(" WHERE ");
                    if(productID != null) stringBuilder.append(" productID = " + productID + " AND");
                    if(count != null) stringBuilder.append(" count >= " + count + " AND");
                    if(minPrice != null) stringBuilder.append(" price >= " + minPrice + " AND");
                    if(maxPrice != null) stringBuilder.append(" price <= " + maxPrice + " AND");
                    if(name != null) stringBuilder.append(" name = '" + name + "' AND");
                    if(productID != null || count != null || minPrice != null || maxPrice != null || name != null) stringBuilder.delete(stringBuilder.length() - 3, stringBuilder.length());
                    stringBuilder.append(";");

                    DataBase.statement.execute(stringBuilder.toString());
                    ResultSet resultSet = DataBase.statement.getResultSet();

                    while(resultSet.next()) {
                        JSONObject product = new JSONObject();
                        product.put("name", resultSet.getString("name"));
                        product.put("count", resultSet.getString("count"));
                        product.put("description", resultSet.getString("description"));
                        product.put("price", resultSet.getString("price"));
                        product.put("productid", resultSet.getString("productid"));
                        product.put("companyid", table_name);
                        product.put("Photo", resultSet.getString("Photo"));
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

    public ResponseEntity<InlineResponse200> getForCompany(@Parameter(in = ParameterIn.HEADER, description = "Company email" ,schema=@Schema()) @RequestHeader(value="companyEmail", required=true) String companyEmail) {
        String accept = request.getHeader("Accept");
        System.out.println("getProducts");
        if (accept != null && accept.contains("application/json")) {
            try {
                DataBase.statement.execute("SELECT * FROM companies WHERE email = '" + companyEmail + "'");
                ResultSet rs = DataBase.statement.getResultSet();
                rs.next();

                Integer table_name = rs.getInt("companyid");
                JSONObject result = new JSONObject();
                JSONArray products = new JSONArray();

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("SELECT * FROM \"" + table_name + "\";");

                DataBase.statement.execute(stringBuilder.toString());
                ResultSet resultSet = DataBase.statement.getResultSet();

                while(resultSet.next()) {
                    JSONObject product = new JSONObject();
                    product.put("name", resultSet.getString("name"));
                    product.put("count", resultSet.getString("count"));
                    product.put("description", resultSet.getString("description"));
                    product.put("price", resultSet.getString("price"));
                    product.put("productid", resultSet.getString("productid"));
                    product.put("companyid", table_name);
                    product.put("Photo", resultSet.getString("Photo"));
                    products.add(product);
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

    public ResponseEntity<Void> postCompany(@Parameter(in = ParameterIn.HEADER, description = "Password" ,required=true,schema=@Schema()) @RequestHeader(value="Password", required=true) String password, @Parameter(in = ParameterIn.HEADER, description = "email" ,required=true,schema=@Schema()) @RequestHeader(value="email", required=true) String email) {
        String accept = request.getHeader("Accept");
        try {
            DataBase.statement.execute("SELECT * FROM companies WHERE email = '" + email + "';");
            ResultSet rs = DataBase.statement.getResultSet();

            if(!rs.next()){
                DataBase.registerCompany(password, email);
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

    public ResponseEntity<Void> putCompany(@Parameter(in = ParameterIn.HEADER, description = "email" ,required=true,schema=@Schema()) @RequestHeader(value="email", required=true) String email,@Parameter(in = ParameterIn.HEADER, description = "Password" ,required=true,schema=@Schema()) @RequestHeader(value="Password", required=true) String password,@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody StorageBody body) {
        String accept = request.getHeader("Accept");

        try {
            DataBase.statement.execute("SELECT * FROM companies WHERE email = '" + email + "';");
            ResultSet rs = DataBase.statement.getResultSet();

            if (rs.next() && rs.getString("password").equals(password)) {
                List<Product> productList = body.getProducts();
                for(Product product : productList){
                    DataBase.replaceProduct(email, product);
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

    public ResponseEntity<Void> checkCompany(@Parameter(in = ParameterIn.HEADER, description = "Password" ,required=true,schema=@Schema()) @RequestHeader(value="Password", required=true) String password, @Parameter(in = ParameterIn.HEADER, description = "email" ,required=true,schema=@Schema()) @RequestHeader(value="email", required=true) String email){
        String accept = request.getHeader("Accept");
        try{
            DataBase.statement.execute("SELECT * FROM companies WHERE email = '" + email + "' AND password = '" + password + "';");
            ResultSet rs = DataBase.statement.getResultSet();

            if(rs.next()){
                return new ResponseEntity<Void>(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }
}
