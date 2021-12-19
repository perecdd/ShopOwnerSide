package io.swagger.api;

import io.swagger.model.Product;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {
    public static ArrayList<Connection> connection = new ArrayList<>();
    public static ArrayList<Statement> statement = new ArrayList<>();
    public static ArrayList<ResultSet> resultSet = new ArrayList<>();

    public DataBase() throws Exception {
        Class.forName("org.sqlite.JDBC");

        connection.add(DriverManager.getConnection("jdbc:sqlite:storage"));
        statement.add(connection.get(0).createStatement());

        connection.add(DriverManager.getConnection("jdbc:sqlite:companyDB"));
        statement.add(connection.get(1).createStatement());

        statement.get(1).execute("CREATE TABLE IF NOT EXISTS companies (\n" +
                "    companyid     INTEGER,\n" +
                "    password      STRING,\n" +
                "    serverAddress STRING\n" +
                ");\n");
    }

    public static void registerCompany(Integer companyID, String password, String serverAddress) throws SQLException {
        DataBase.statement.get(1).execute("INSERT INTO companies (\n" +
                "                          companyID,\n" +
                "                          password,\n" +
                "                          serverAddress\n" +
                "                      )\n" +
                "                      VALUES (\n" +
                "                          '" + companyID + "',\n" +
                "                          '" + password + "',\n" +
                "                          '" + serverAddress + "'\n" +
                "                      );\n");
        addCompany(companyID);
    }

    public static void replaceProduct(Integer companyID, Product product) throws SQLException {
        statement.get(0).execute("INSERT INTO [" + companyID + "] (\n" +
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
                "                );");
    }

    public static void addCompany(Integer companyID) throws SQLException {
        statement.get(0).execute("CREATE TABLE [" + companyID + "] (\n" +
                "    productid   INTEGER UNIQUE ON CONFLICT REPLACE,\n" +
                "    photo       STRING,\n" +
                "    name        STRING,\n" +
                "    count       INTEGER,\n" +
                "    description STRING,\n" +
                "    price       INTEGER NOT NULL\n" +
                ");\n");
    }
}