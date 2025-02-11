/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package liquorstoremanagement.dao;

/**
 *
 * @author MATIAN PC
 */

import liquorstoremanagement.model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // CREATE: Add a new product
    public void addProduct(Product product) {
        String sql = "INSERT INTO products(name, category, brand, quantity, price, alcohol_percentage) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getCategory());
            pstmt.setString(3, product.getBrand());
            pstmt.setInt(4, product.getQuantity());
            pstmt.setDouble(5, product.getPrice());
            pstmt.setDouble(6, product.getAlcoholPercentage());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ: Retrieve all products
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getString("brand"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getDouble("alcohol_percentage")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // UPDATE: Update an existing product
    public void updateProduct(Product product) {
        String sql = "UPDATE products SET name=?, category=?, brand=?, quantity=?, price=?, alcohol_percentage=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getCategory());
            pstmt.setString(3, product.getBrand());
            pstmt.setInt(4, product.getQuantity());
            pstmt.setDouble(5, product.getPrice());
            pstmt.setDouble(6, product.getAlcoholPercentage());
            pstmt.setInt(7, product.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE: Remove a product by its ID
    public void deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}