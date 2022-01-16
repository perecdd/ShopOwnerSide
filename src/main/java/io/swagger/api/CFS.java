package io.swagger.api;

import java.net.HttpURLConnection;
import java.net.URL;

public class CFS {
    public final static Integer port = 3939;
    public final static String ip = "http://localhost"; // TODO http://cfs
    public static HttpURLConnection con;

    static boolean loginUser(String email, String password){
        try {
            URL url = new URL (ip + ":" + port + "/user");

            con = (HttpURLConnection)url.openConnection();
            con.setRequestProperty("User-Agent", "ShopOwnerApplication");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("email", email);
            con.setRequestProperty("password", password);
            con.setRequestMethod("GET");
            con.setDoOutput(true);
            con.setDoInput(true);

            int responseCode = con.getResponseCode();
            con.disconnect();

            return responseCode == 200;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
