/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package liquorstoremanagement.dao;

/**
 *
 * @author MATIAN PC
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Update the URL, USER, and PASSWORD as per your database configuration.
    private static final String URL = "jdbc:mysql://localhost:3306/liquor_store";
    private static final String USER = "root";
    private static final String PASSWORD = "56789";

    public static Connection getConnection() {
        try {
            // For JDBC 4.0 and above, the driver is auto-loaded if in the classpath.
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}