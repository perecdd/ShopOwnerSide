package io.swagger.api;

import io.swagger.model.InlineResponse2002;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.postgresql.jdbc.PgArray;
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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-24T11:40:06.895Z[GMT]")
@RestController
public class TicketUserApiController implements TicketUserApi {

    private static final Logger log = LoggerFactory.getLogger(TicketUserApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public TicketUserApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<InlineResponse2002>> ticketUserGet(@Parameter(in = ParameterIn.HEADER, description = "User's email" ,required=true,schema=@Schema()) @RequestHeader(value="email", required=true) String email, @Parameter(in = ParameterIn.HEADER, description = "User's password" ,required=true,schema=@Schema()) @RequestHeader(value="password", required=true) String password) {
        String accept = request.getHeader("Accept");
        try {
            DataBase.statement.execute("SELECT * FROM tickets WHERE email = '" + email + "' AND password = '" + password + "';");
            ResultSet resultSet = DataBase.statement.getResultSet();
            JSONArray result = new JSONArray();
            while(resultSet.next()){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", resultSet.getInt("id"));
                jsonObject.put("status", resultSet.getString("status"));

                JSONArray jsonArray = new JSONArray();
                PgArray sqlProducts = (PgArray) resultSet.getArray("products");
                ResultSet rs = null;
                if(sqlProducts != null) rs = sqlProducts.getResultSet();
                for(int i = 0; sqlProducts != null && rs.next(); i++){
                    StringBuilder sb = new StringBuilder(rs.getString(2));
                    sb.delete(0, 1);
                    sb.delete(sb.length() - 1, sb.length());

                    String[] objs = sb.toString().split(",");

                    JSONObject JO = new JSONObject();
                    JO.put("name", objs[0].replaceAll("\"", ""));
                    JO.put("Photo", objs[1].replaceAll("\"", ""));
                    JO.put("companyid", Integer.parseInt(objs[2]));
                    JO.put("productid", Integer.parseInt(objs[3]));
                    JO.put("price", Integer.parseInt(objs[4]));
                    JO.put("count", Integer.parseInt(objs[5]));
                    JO.put("description", objs[6].replaceAll("\"", ""));
                    jsonArray.add(JO);
                }
                jsonObject.put("products", jsonArray);

                result.add(jsonObject);
            }
            return new ResponseEntity<List<InlineResponse2002>>(objectMapper.readValue(result.toString(), List.class), HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<List<InlineResponse2002>>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Void> ticketUserPost(@Parameter(in = ParameterIn.HEADER, description = "User's email" ,required=true,schema=@Schema()) @RequestHeader(value="email", required=true) String email, @Parameter(in = ParameterIn.HEADER, description = "User's password" ,required=true,schema=@Schema()) @RequestHeader(value="password", required=true) String password, @Parameter(in = ParameterIn.HEADER, description = "Order ID." ,required=true,schema=@Schema()) @RequestHeader(value="ticket", required=true) Integer ticket) {
        String accept = request.getHeader("Accept");
        try {
            DataBase.statement.execute("UPDATE tickets SET status = 'canceled' WHERE password = '"+password+"' AND email = '" + email + "' AND id = " + ticket + " AND status != 'success';");
            DataBase.statement.execute("UPDATE tickets SET status = 'successful' WHERE password = '"+password+"' AND email = '" + email + "' AND id = " + ticket + " AND status = 'success';");
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }
}
