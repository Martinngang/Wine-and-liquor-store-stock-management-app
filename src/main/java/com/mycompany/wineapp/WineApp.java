/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.wineapp;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import liquorstoremanagement.dao.DatabaseConnection; // Import your DatabaseConnection class
import javax.swing.SwingUtilities;
import liquorstoremanagement.ui.ProductManagementForm; // or whatever class is your UI entry point

public class WineApp {

    public static void main(String[] args) {
        // Launch your UI in a separate thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Launch your UI
                new ProductManagementForm().setVisible(true);
            }
        });

        // Handle the database connection in the main method
        Connection conn = DatabaseConnection.getConnection();

        // Ensure that the connection is not null
        if (conn != null) {
            try {
                // Create a statement to execute SQL queries
                Statement stmt = conn.createStatement();
                
                // Example query to fetch all wines
                String query = "SELECT * FROM wines"; // Update the table name as necessary
                ResultSet rs = stmt.executeQuery(query);
                
                // Process the results
                while (rs.next()) {
                    // Retrieve data from each row (example: wine name and price)
                    String wineName = rs.getString("wine_name"); // Adjust column name as necessary
                    double price = rs.getDouble("price"); // Adjust column name as necessary
                    System.out.println("Wine: " + wineName + ", Price: " + price);
                }
                
            } catch (SQLException e) {
                System.out.println("Error executing query: " + e.getMessage());
            } finally {
                // Close the connection safely
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Error closing connection: " + e.getMessage());
                }
            }
        } else {
            System.out.println("Failed to establish a database connection.");
        }
    }
}