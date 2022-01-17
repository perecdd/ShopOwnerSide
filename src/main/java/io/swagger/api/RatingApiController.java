package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.Rating;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-19T09:20:14.611Z[GMT]")
@RestController
public class RatingApiController implements RatingApi {
    private static final Logger log = LoggerFactory.getLogger(StorageApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public RatingApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> rateCompany(@Parameter(in = ParameterIn.HEADER, description = "The user's email address for identification." ,required=true,schema=@Schema()) @RequestHeader(value="email", required=true) String email, @Parameter(in = ParameterIn.HEADER, description = "User password." ,required=true,schema=@Schema()) @RequestHeader(value="Password", required=true) String password, @Parameter(in = ParameterIn.HEADER, description = "User rating." ,required=true,schema=@Schema()) @RequestHeader(value="rating", required=true) Integer rating, @Parameter(in = ParameterIn.HEADER, description = "Company ID in the database." ,required=true,schema=@Schema()) @RequestHeader(value="companyid", required=true) Integer companyid){
        String accept = request.getHeader("Accept");
        if(CFS.loginUser(email, password)){
            try {
                DataBase.statement.execute("UPDATE companies SET appraisers = appraisers + 1, rating = rating + " + rating + " WHERE companyid = " + companyid + ";");
                return new ResponseEntity<Void>(HttpStatus.OK);
            }
            catch (Exception e){
                e.printStackTrace();
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            }
        }
        else{
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<Rating> getRating(@Parameter(in = ParameterIn.HEADER, description = "Company ID in the database." ,required=false,schema=@Schema()) @RequestHeader(value="companyid", required=false) Integer companyid, @Parameter(in = ParameterIn.HEADER, description = "The email address for identifying the company in the database." ,required=false,schema=@Schema()) @RequestHeader(value="email", required=false) String email){
        String accept = request.getHeader("Accept");
        try {
            if(companyid != null) {
                DataBase.statement.execute("SELECT * FROM companies WHERE companyid = " + companyid + ";");
                ResultSet resultSet = DataBase.statement.getResultSet();
                if (resultSet.next()) {
                    Double value = Double.valueOf(resultSet.getInt("appraisers") == 0 ? 0 : Double.valueOf(resultSet.getInt("rating")) / Double.valueOf(resultSet.getInt("appraisers")));
                    return new ResponseEntity<Rating>(objectMapper.readValue("{ \"rating\": " + value + ", \"name\": \"" + resultSet.getString("name").replaceAll("\"", "") + "\" }", Rating.class), HttpStatus.OK);
                } else {
                    return new ResponseEntity<Rating>(HttpStatus.BAD_REQUEST);
                }
            }
            else{
                DataBase.statement.execute("SELECT * FROM companies WHERE email = '" + email + "';");
                ResultSet resultSet = DataBase.statement.getResultSet();
                if (resultSet.next()) {
                    Double value = Double.valueOf(resultSet.getInt("appraisers") == 0 ? 0 : Double.valueOf(resultSet.getInt("rating")) / Double.valueOf(resultSet.getInt("appraisers")));
                    return new ResponseEntity<Rating>(objectMapper.readValue("{ \"rating\": " + value + ", \"name\": \"" + resultSet.getString("name").replaceAll("\"", "") + "\" }", Rating.class), HttpStatus.OK);
                } else {
                    return new ResponseEntity<Rating>(HttpStatus.BAD_REQUEST);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<Rating>(HttpStatus.BAD_REQUEST);
        }
    }
}
