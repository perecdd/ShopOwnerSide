package io.swagger.api;

import io.swagger.model.Address;
import io.swagger.model.Company;
import io.swagger.model.Product;
import io.swagger.model.User;
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
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import io.swagger.api.DataBase;

class JSONUtility {
    public static JSONObject AddressToJson(Address address){
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("city", address.getCity());
        jsonObject.put("country", address.getCountry());
        jsonObject.put("street", address.getStreet());
        jsonObject.put("house", address.getHouse());
        jsonObject.put("flat", address.getFlat());

        return jsonObject;
    }

    public static JSONArray BasketToJson(List<Product> basket, Integer companyID){
        JSONArray jsonArray = new JSONArray();
        for(Product product : basket){
            if(companyID.equals(product.getCompanyid().intValue())) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", product.getName());
                jsonObject.put("companyid", product.getCompanyid());
                jsonObject.put("count", product.getCount());
                jsonObject.put("description", product.getDescription());
                jsonObject.put("price", product.getPrice());
                jsonObject.put("productid", product.getProductid());
                jsonObject.put("Photo", product.getPhoto());
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    public static JSONObject UserToJson(User user, Integer companyID) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("address", AddressToJson(user.getAddress()));
        jsonObject.put("name", user.getName());
        jsonObject.put("surname", user.getSurname());
        jsonObject.put("phone", user.getPhone());
        jsonObject.put("email", user.getEmail());
        jsonObject.put("id", user.getId());
        jsonObject.put("basket", BasketToJson(user.getBasket(), companyID));

        return jsonObject;
    }
}

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-19T09:20:14.611Z[GMT]")
@RestController
public class OrderApiController implements OrderApi {

    private static final Logger log = LoggerFactory.getLogger(OrderApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public OrderApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> postOrder(@Parameter(in = ParameterIn.DEFAULT, description = "Give all information about user to company", schema=@Schema()) @Valid @RequestBody User body) {
        String accept = request.getHeader("Accept");

        try {
            List<Product> productList = body.getBasket();
            Map<Integer, List<Product>> sortedProducts = new HashMap<>();

            var iter = productList.listIterator();
            while (iter.hasNext()) {
                Product product = iter.next();
                if (!sortedProducts.containsKey(product.getCompanyid())) {
                    sortedProducts.put(product.getCompanyid().intValue(), new ArrayList<>());
                }
                sortedProducts.get(product.getCompanyid().intValue()).add(product);
            }

            for (Map.Entry<Integer, List<Product>> entry : sortedProducts.entrySet()) {
                Integer key = entry.getKey();
                List<Product> value = entry.getValue();

                StringBuilder productString = new StringBuilder();
                productString.append("ARRAY[");

                //PRODUCT AS (name TEXT, photo TEXT, companyid integer, productid integer, price integer, count integer, description TEXT);

                for (Product product : value) {
                    productString.append("('" + product.getName() + "', '" + product.getPhoto() + "', " + product.getCompanyid() + ", " + product.getProductid() + ", " + product.getPrice() + ", " + product.getCount() + ", '" + product.getDescription() + "')::PRODUCT,");
                }
                productString.delete(productString.length() - 1, productString.length());
                productString.append("]");

                DataBase.statement.execute("INSERT INTO tickets (\n" +
                        "                          companyID,\n" +
                        "                          userid,\n" +
                        "                          products\n" +
                        "                      )\n" +
                        "                      VALUES (\n" +
                        "                          '" + key + "',\n" +
                        "                          '" + body.getId() + "',\n" +
                        "                          " + productString.toString() + "\n" +
                        "                      );\n");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        /*List<Product> productList = body.getBasket();
        String request = new String();
        request = "SELECT companyid, serverAddress FROM companies WHERE";
        for(Product product : productList){
            request += " companyid == " + product.getCompanyid();
            request += " OR";
        }
        request = request.substring(0, request.length() - 2);
        request += ";";

        try {
            DataBase.statement.get(1).execute(request);
            ResultSet rs = DataBase.statement.get(1).getResultSet();

            while (rs.next()) {
                String address = rs.getString("serverAddress");

                URL url = new URL(address + "/order");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestProperty("User-Agent", "ShopOwnerApplication");
                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestMethod("POST");

                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(con.getOutputStream())), true);
                out.println(JSONUtility.UserToJson(body, rs.getInt("companyid")).toString());

                int responseCode = con.getResponseCode();

                if (responseCode != 200) {
                    log.error("An error occurred when sending data to the address " + address);
                }

                out.close();
                con.disconnect();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        }*/
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
