package io.swagger.api;

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
                "    companyid     INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    password      STRING,\n" +
                "    serverAddress STRING\n" +
                ");\n");
    }

    public static void addCompany(Integer companyID) throws SQLException {
        statement.get(0).execute("CREATE TABLE [" + companyID + "] (\n" +
                "    productid   INTEGER UNIQUE,\n" +
                "    photo       STRING,\n" +
                "    count       INTEGER,\n" +
                "    description STRING,\n" +
                "    price       INTEGER NOT NULL\n" +
                ");\n");
    }
}