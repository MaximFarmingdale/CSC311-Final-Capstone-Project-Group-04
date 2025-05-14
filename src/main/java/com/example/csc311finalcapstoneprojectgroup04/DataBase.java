package com.example.csc311finalcapstoneprojectgroup04;

import java.sql.*;

/**
 * Stores user information and also checks if a sign-in is valid or if
 * a name is unique.
 */
public class DataBase {
    final String MYSQL_SERVER_URL = "jdbc:mysql://nikiforovcsc311.mysql.database.azure.com/";
    final String DB_URL =  MYSQL_SERVER_URL + "Capstone";
    final String USERNAME = "nikim";
    final String PASSWORD = "CSC311password";

    /**
     * creates the database
     */
    public void connectToDatabase() {
        try {
            Connection conn = DriverManager.getConnection(MYSQL_SERVER_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS Capstone");
            statement.close();
            conn.close();
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            statement = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS users ("
                    + "username VARCHAR(200) NOT NULL PRIMARY KEY,"
                    + "password VARCHAR(200) NOT NULL UNIQUE)";
            statement.executeUpdate(sql);
            statement.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * adds a user to the database
     * @param username
     * @param password
     * @return whether the insert worked successfully
     */
    public boolean addUser(String username, String password) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO users(username, password)" +
                    "values (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            int temp = statement.executeUpdate();
            statement.close();
            conn.close();
            if(temp > 0)//won't be higher than one
                return true;
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks if the username is unique.
     * @param username
     * @return
     */
    public boolean checkDistinct(String username) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT username FROM users WHERE username = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            boolean distinct = !rs.next();
            rs.close();
            statement.close();
            conn.close();
            return  distinct;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Checks if a sign-in is valid.
     * @param username
     * @param password
     * @return
     */
    public boolean validLogin(String username, String password) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT username FROM users WHERE (username = ? AND password = ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            Boolean valid = rs.next();
            rs.close();
            statement.close();
            conn.close();
            return valid;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
