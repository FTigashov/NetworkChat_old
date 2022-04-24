package com.example.networkchat.server.options.authentication;
import java.sql.*;


public class DBAuthentication implements AuthenticationService {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    @Override
    public String getUsernameByLoginAndPassword(String login, String password) {
        try {
            resultSet = statement.executeQuery("SELECT * FROM authentication");
            while (resultSet.next()) {
                if (resultSet.getString("login").equals(login)
                        && resultSet.getString("password").equals(password)) {
                    return resultSet.getString("username");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void startAuthentication() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/com/example/networkchat/databases/mainDB.db");
            statement = connection.createStatement();
        } catch (ClassNotFoundException |  SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endAuthentication() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
