/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package liquorstoremanagement.model;

/**
 *
 * @author MATIAN PC
 */

public class Order {
    private String clientUsername;
    private int productId;
    private int quantity;
    private double totalAmount;

    public Order(String clientUsername, int productId, int quantity, double totalAmount) {
        this.clientUsername = clientUsername;
        this.productId = productId;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }

    // Getters (and setters if needed)
    public String getClientUsername() {
        return clientUsername;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}