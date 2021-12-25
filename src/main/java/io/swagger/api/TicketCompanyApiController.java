package io.swagger.api;

import io.swagger.model.InlineResponse2001;
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
import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-24T11:40:06.895Z[GMT]")
@RestController
public class TicketCompanyApiController implements TicketCompanyApi {

    private static final Logger log = LoggerFactory.getLogger(TicketCompanyApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public TicketCompanyApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<InlineResponse2001>> getTicket(@Parameter(in = ParameterIn.HEADER, description = "" ,required=true,schema=@Schema()) @RequestHeader(value="companyid", required=true) String companyid,@Parameter(in = ParameterIn.HEADER, description = "" ,required=true,schema=@Schema()) @RequestHeader(value="password", required=true) String password) {
        String accept = request.getHeader("Accept");

        try {
            DataBase.statement.execute("SELECT * FROM companies WHERE companyid = " + companyid + " AND password = '" + password + "';");
            ResultSet resultSet = DataBase.statement.getResultSet();
            if(resultSet.next()) {
                DataBase.statement.execute("SELECT * FROM tickets WHERE companyid = " + companyid + ";");
                ResultSet rs = DataBase.statement.getResultSet();

                JSONArray AllTickets = new JSONArray();

                while(rs.next()) {
                    JSONObject result = new JSONObject();

                    JSONArray jsonArray = new JSONArray();
                    JSONObject address = new JSONObject();

                    address.put("city", rs.getString("city"));
                    address.put("country", rs.getString("country"));
                    address.put("flat", rs.getString("flat"));
                    address.put("house", rs.getString("house"));
                    address.put("street", rs.getString("street"));

                    result.put("email", rs.getString("email"));
                    result.put("id", rs.getString("id"));
                    result.put("name", rs.getString("name"));
                    result.put("surname", rs.getString("surname"));
                    result.put("phone", rs.getString("phone"));
                    result.put("address", address);

                    PgArray sqlProducts = (PgArray) rs.getArray("products");
                    ResultSet resultSet1 = sqlProducts.getResultSet();
                    for(int i = 0; resultSet1.next(); i++){
                        StringBuilder sb = new StringBuilder(resultSet1.getString(2));
                        sb.delete(0, 1);
                        sb.delete(sb.length() - 1, sb.length());

                        String[] objs = sb.toString().split(",");

                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("name", objs[0]);
                        jsonObject.put("Photo", objs[1]);
                        jsonObject.put("companyid", Integer.parseInt(objs[2]));
                        jsonObject.put("productid", Integer.parseInt(objs[3]));
                        jsonObject.put("price", Integer.parseInt(objs[4]));
                        jsonObject.put("count", Integer.parseInt(objs[5]));
                        jsonObject.put("description", objs[6]);
                        jsonArray.add(jsonObject);

                        result.put("products", jsonArray);

                        AllTickets.add(result);
                    }
                }

                return new ResponseEntity<List<InlineResponse2001>>(objectMapper.readValue(AllTickets.toString(), List.class), HttpStatus.OK);
            }
            else{
                return new ResponseEntity<List<InlineResponse2001>>(HttpStatus.UNAUTHORIZED);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<List<InlineResponse2001>>(HttpStatus.NOT_IMPLEMENTED);
        }
    }

    public ResponseEntity<Void> ticketCompanyPost(@Parameter(in = ParameterIn.HEADER, description = "" ,required=true,schema=@Schema()) @RequestHeader(value="companyid", required=true) String companyid,@Parameter(in = ParameterIn.HEADER, description = "" ,required=true,schema=@Schema()) @RequestHeader(value="password", required=true) String password,@Parameter(in = ParameterIn.HEADER, description = "" ,required=true,schema=@Schema()) @RequestHeader(value="status", required=true) String status,@Parameter(in = ParameterIn.HEADER, description = "" ,required=true,schema=@Schema()) @RequestHeader(value="ticket", required=true) Integer ticket) {
        String accept = request.getHeader("Accept");
        try {
            DataBase.statement.execute("SELECT * FROM companies WHERE companyid = " + companyid + " AND password = '" + password + "';");
            ResultSet resultSet = DataBase.statement.getResultSet();
            if(resultSet.next() && !resultSet.getString("status").equals("canceled")){
                DataBase.statement.execute("UPDATE tickets SET status = '"+status+"' WHERE companyid = " + companyid + " AND id = " + ticket + ";");
            }
            else{
                return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
