
package INVENTORY;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
//import javax.swing.JSpinner;
import javax.swing.JTextField;

public class OrderPlacement {
     
    public void order(ArrayList<String> orderProductArray, ArrayList<Integer> orderQuantityArray, JTextField orderLocation, String email_order) {
        // Connection to the database
        Connections connect = new Connections();
        // SQL query to insert order
        String insertQuery = "INSERT INTO order_placement (email_order, products_order, quantity_order, total_price_order, location_order) VALUES (?, ?, ?, ?, ?)";
        // SQL query to get product price from inventory
        String priceQuery = "SELECT price FROM inventory WHERE product = ?"; 
        // SQL query to update product quantity/sale in inventory
        String updateInventoryQuery = "UPDATE inventory SET quantity = quantity - ?, sale = sale + ?, price = ? WHERE product = ?";  
        String location_order = orderLocation.getText(); 
        try (Connection connection = connect.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
             PreparedStatement priceStatement = connection.prepareStatement(priceQuery);
             PreparedStatement updateStatement = connection.prepareStatement(updateInventoryQuery)) { 
            // Loop to insert each product and compute the total
            for (int i = 0; i < orderProductArray.size(); i++) {
                String products_order = orderProductArray.get(i);
                int quantity_order = orderQuantityArray.get(i);  
                // Get the price of the product
                priceStatement.setString(1, products_order);
                ResultSet rs = priceStatement.executeQuery(); 
                double price_per_item = 0.0;
                if (rs.next()) {
                    price_per_item = rs.getDouble("price");
                } else {
                    System.err.println("Error: Product " + products_order + " not found in inventory.");
                    continue; // Skip to the next product
                } 
                double total_price_order = quantity_order * price_per_item; 
                // Set the parameters for the PreparedStatement
                insertStatement.setString(1, email_order);
                insertStatement.setString(2, products_order);
                insertStatement.setInt(3, quantity_order);
                insertStatement.setDouble(4, total_price_order);
                insertStatement.setString(5, location_order); 
                // Execute the update for each product
                insertStatement.executeUpdate(); 
                // Update the inventory quantity
                updateStatement.setInt(1, quantity_order);
                updateStatement.setInt(2, quantity_order);
                updateStatement.setDouble(3, price_per_item);
                updateStatement.setString(4, products_order);
                updateStatement.executeUpdate();
            } 
            System.out.println("Orders placed successfully!");    
        } catch (SQLException e) {
            System.err.println("Error inserting product: " + e.getMessage());
        }
    }
    
    public Boolean ProductChecker(String orderProduct, int orderQuantity) {
        // Connection to the database
        Connections connect = new Connections();
        boolean isTrue = false;
        // SQL query to check for orderProduct and orderQuantity
        String checkQuery = "SELECT price, quantity FROM inventory WHERE product = ?";
        try (Connection connection = connect.getConnection();
             PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {   
            // Set the product parameter
            checkStatement.setString(1, orderProduct);
            ResultSet rs = checkStatement.executeQuery();
            // Check if the product exists and has enough quantity
            if (rs.next()) {
                int available_quantity = rs.getInt("quantity");
                if (orderQuantity <= available_quantity) {
                    isTrue = true;
                }
            } else {
                System.err.println("Error: Product " + orderProduct + " not found in inventory.");
            }
            
        } catch (SQLException e) {
            System.err.println("Error checking product: " + e.getMessage());
        }
        return isTrue;
    }  
}
