/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package liquorstoremanagement.dao;

import liquorstoremanagement.model.Sale;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleDAO {

    // Add a new sale record
    public void addSale(Sale sale) {
        String sql = "INSERT INTO sales(product_id, quantity_sold, total_price, sale_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, sale.getProductId());
            pstmt.setInt(2, sale.getQuantitySold());
            pstmt.setDouble(3, sale.getTotalPrice());
            // Convert java.util.Date to java.sql.Date
            pstmt.setDate(4, new java.sql.Date(sale.getSaleDate().getTime()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update an existing sale record
    public void updateSale(Sale sale) {
        String sql = "UPDATE sales SET product_id = ?, quantity_sold = ?, total_price = ?, sale_date = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, sale.getProductId());
            pstmt.setInt(2, sale.getQuantitySold());
            pstmt.setDouble(3, sale.getTotalPrice());
            pstmt.setDate(4, new java.sql.Date(sale.getSaleDate().getTime()));
            pstmt.setInt(5, sale.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a sale by ID
    public void deleteSale(int id) {
        String sql = "DELETE FROM sales WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve all sales records
    public List<Sale> getAllSales() {
        List<Sale> sales = new ArrayList<>();
        String sql = "SELECT * FROM sales";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Sale sale = new Sale(
                    rs.getInt("id"),
                    rs.getInt("product_id"),
                    rs.getInt("quantity_sold"),
                    rs.getDouble("total_price"),
                    rs.getDate("sale_date")
                );
                sales.add(sale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sales;
    }
}