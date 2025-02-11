/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package liquorstoremanagement.model;

/**
 *
 * @author MATIAN PC
 */

public class Product {
    private int id;
    private String name;
    private String category;
    private String brand;
    private int quantity;
    private double price;
    private double alcoholPercentage;
    private String imagePath;

    // Constructor with ID (for reading/updating existing products)
    public Product(int id, String name, String category, String brand, int quantity, double price, double alcoholPercentage) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.quantity = quantity;
        this.price = price;
        this.alcoholPercentage = alcoholPercentage;
    }

    // Constructor without ID (for creating new products, as ID is auto-generated)
    public Product(String name, String category, String brand, int quantity, double price, double alcoholPercentage) {
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.quantity = quantity;
        this.price = price;
        this.alcoholPercentage = alcoholPercentage;
    }
    
    public String getImagePath() {
    // Construct the path using the product ID
    return "src/main/java/" + this.id + ".jpg";
    }

    // Getters and Setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getAlcoholPercentage() { return alcoholPercentage; }
    public void setAlcoholPercentage(double alcoholPercentage) { this.alcoholPercentage = alcoholPercentage; }
}