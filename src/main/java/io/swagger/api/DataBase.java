package io.swagger.api;

import io.swagger.model.Product;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {
    public static Connection connection;
    public static Statement statement;

    public DataBase() throws Exception {
        Class.forName("org.postgresql.Driver");

        StringBuilder url = new StringBuilder();
        url.
                append("jdbc:postgresql://").  //db type
                append("localhost:").          //host name
                append("5432/").               //port
                append("postgres?").             //db name
                append("user=postgres&").      //login
                append("password=postgres");     //password

        connection = DriverManager.getConnection(url.toString());
        statement = connection.createStatement();

        statement.execute("CREATE TABLE IF NOT EXISTS companies (\n" +
                "    companyid     INTEGER,\n" +
                "    password      TEXT\n" +
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

    public static void registerCompany(Integer companyID, String password) throws SQLException {
        DataBase.statement.execute("INSERT INTO companies (\n" +
                "                          companyID,\n" +
                "                          password\n" +
                "                      )\n" +
                "                      VALUES (\n" +
                "                          '" + companyID + "',\n" +
                "                          '" + password + "'\n" +
                "                      );\n");
        addCompany(companyID);
    }

    public static void replaceProduct(Integer companyID, Product product) throws SQLException {
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
                "    productid   INTEGER UNIQUE,\n" +
                "    photo       TEXT,\n" +
                "    name        TEXT,\n" +
                "    count       INTEGER,\n" +
                "    description TEXT,\n" +
                "    price       INTEGER NOT NULL\n" +
                ");\n");
    }
}