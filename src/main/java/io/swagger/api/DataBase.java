package io.swagger.api;

import io.swagger.model.Product;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.sql.*;

public class DataBase {
    public static Connection connection;
    public static Statement statement;

    public DataBase() throws Exception {
        Class.forName("org.postgresql.Driver");

        StringBuilder url = new StringBuilder();
        url.
        append("jdbc:postgresql://").  //db type
        append("postgres:").          //host name
        append("5432/").               //port
        append("postgres?").             //db name
        append("user=postgres&").      //login
        append("password=postgres");     //password*/

        connection = DriverManager.getConnection(url.toString());
        statement = connection.createStatement();

        statement.execute("CREATE TABLE IF NOT EXISTS companies (\n" +
                "    name     TEXT,\n" +
                "    companyid     SERIAL,\n" +
                "    email     TEXT UNIQUE,\n" +
                "    password      TEXT,\n" +
                "    rating     INTEGER DEFAULT 0,\n" +
                "    appraisers      INTEGER DEFAULT 0,\n" +
                "    accepted      INTEGER DEFAULT 0\n" +
                ");");

        statement.execute("DO\n" +
                "$$\n" +
                "BEGIN\n" +
                "  IF NOT EXISTS (SELECT *\n" +
                "                        FROM pg_type typ\n" +
                "                             INNER JOIN pg_namespace nsp\n" +
                "                                        ON nsp.oid = typ.typnamespace\n" +
                "                        WHERE nsp.nspname = current_schema()\n" +
                "                              AND typ.typname = 'product') THEN\n" +
                "    CREATE TYPE PRODUCT AS (name TEXT, photo TEXT, companyid integer, productid integer, price integer, count integer, description TEXT);" +
                "  END IF;\n" +
                "END;\n" +
                "$$\n" +
                "LANGUAGE plpgsql;");

        statement.execute("CREATE TABLE IF NOT EXISTS tickets (\n" +
                "    id     SERIAL PRIMARY KEY,\n" +
                "    status     TEXT,\n" +
                "    companyid     INTEGER,\n" +
                "    userid      INTEGER,\n" +
                "    email   TEXT NOT NULL,\n" +
                "    password   TEXT NOT NULL,\n" +
                "    name    TEXT  NOT NULL,\n" +
                "    surname TEXT  NOT NULL,\n" +
                "    phone   TEXT,\n" +
                "    city    TEXT  NOT NULL,\n" +
                "    country TEXT  NOT NULL,\n" +
                "    street  TEXT  NOT NULL,\n" +
                "    house   TEXT  NOT NULL,\n" +
                "    flat    TEXT  NOT NULL,\n" +
                "    products Product[]\n" +
                ");");
    }

    public static void registerCompany(String password, String email, String name) throws SQLException {
        DataBase.statement.execute("INSERT INTO companies (\n" +
                "                          email,\n" +
                "                          password,\n" +
                "                          name\n" +
                "                      )\n" +
                "                      VALUES (\n" +
                "                          '" + email + "',\n" +
                "                          '" + password + "',\n" +
                "                          '" + name + "'\n" +
                "                      );\n");
        DataBase.statement.execute("SELECT * FROM companies WHERE email = '"+email+"'");
        ResultSet resultSet = DataBase.statement.getResultSet();
        resultSet.next();
        addCompany(resultSet.getInt("companyid"));
    }

    public static void replaceProduct(String email, Product product) throws SQLException {
        statement.execute("SELECT * FROM companies WHERE email = '"+email+"'");
        ResultSet resultSet = statement.getResultSet();
        resultSet.next();
        Integer companyID = resultSet.getInt("companyid");

        statement.execute("INSERT INTO \"" + companyID + "\" (\n" +
                "                    productid,\n" +
                "                    photo,\n" +
                "                    name,\n" +
                "                    count,\n" +
                "                    description,\n" +
                "                    price\n" +
                "                )\n" +
                "                VALUES (\n" +
                "                    " + product.getProductid() + ",\n" +
                "                    '" + product.getPhoto() + "',\n" +
                "                    '" + product.getName() + "',\n" +
                "                    " + product.getCount() +",\n" +
                "                    '" + product.getDescription() + "',\n" +
                "                    " + product.getPrice() + "\n" +
                "                ) ON CONFLICT (productid) DO UPDATE SET photo = excluded.photo," +
                "name = excluded.name," +
                "count = excluded.count," +
                "description = excluded.description," +
                "price = excluded.price;");
    }

    public static void addCompany(Integer companyID) throws SQLException {
        statement.execute("CREATE TABLE public.\"" + companyID + "\" (\n" +
                "    productid   SERIAL UNIQUE,\n" +
                "    photo       TEXT,\n" +
                "    name        TEXT,\n" +
                "    count       INTEGER,\n" +
                "    description TEXT,\n" +
                "    price       INTEGER NOT NULL\n" +
                ");\n");

        Product product = new Product();
        product.setName("Hello world!");
        product.setDescription("Hello world!");
        product.setCount(BigDecimal.valueOf(0));
        product.setPrice(BigDecimal.valueOf(0));
        product.setPhoto("Use link there");

        statement.execute("INSERT INTO \"" + companyID + "\" (\n" +
                "                    photo,\n" +
                "                    name,\n" +
                "                    count,\n" +
                "                    description,\n" +
                "                    price\n" +
                "                )\n" +
                "                VALUES (\n" +
                "                    '" + product.getPhoto() + "',\n" +
                "                    '" + product.getName() + "',\n" +
                "                    " + product.getCount() +",\n" +
                "                    '" + product.getDescription() + "',\n" +
                "                    " + product.getPrice() + "\n" +
                "                );");
    }
}