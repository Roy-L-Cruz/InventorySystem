
package INVENTORY;
import com.toedter.calendar.JDateChooser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Inventory {
    
    public void insertIntoDatabase(JTextField addProduct, JComboBox addCategory, JTextField addPrice, JSpinner AddQuantity, JDateChooser addExpiration, JTextArea AddDescription, JLabel addImage, String path, String supplier){
        // Connection to the database
        Connections connect = new Connections();
        // SQL query
        String query = "INSERT INTO inventory (product, category, price, cost_price, supplier, quantity, expiration, description, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        // Set the data to variables
        String productName = addProduct.getText();
        String category = (String) addCategory.getSelectedItem();
        double price = Double.parseDouble(addPrice.getText());
        int quantity = AddQuantity.getComponentCount();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String expiration = dateFormat.format(addExpiration.getDate()); // Assuming addExpiration is a JDateChooser
        String description = AddDescription.getText();
        InputStream imageStream = null;
        try {
            imageStream = new FileInputStream(new File(path));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection connection = connect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            // Set parameters
            statement.setString(1, productName);
            statement.setString(2, category);
            statement.setDouble(3, price * 1.10);
            statement.setDouble(4, price);
            statement.setString(5, supplier);
            statement.setInt(6, quantity);
            statement.setString(7, expiration);
            statement.setString(8, description);
            statement.setBlob(9, imageStream);
            // Execute the query
            statement.executeUpdate();         
            System.out.println("Product inserted successfully.");        
        } catch (SQLException e) {
            System.err.println("Error inserting product: " + e.getMessage());
        } finally {
            if (imageStream != null) {
                try {
                    imageStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void generateReceipt(ArrayList<String> orderProductArray, ArrayList<Integer> orderQuantityArray, ArrayList<Double> prices, JTextArea orderReceipt) {
        // Append receipt header to JTextArea
        
        orderReceipt.append("Receipt\n----------------------------------------------\n");
        orderReceipt.append(String.format("%-20s %-8s %-10s %-10s\n", "Product", "Quantity", "Price", "Total"));
        orderReceipt.append("----------------------------------------------\n");
        double totalAmount = 0.0;
        // Append each product entry to JTextArea
        for (int i = 0; i < orderProductArray.size(); i++) {
            
            String product = orderProductArray.get(i);
            int quantity = orderQuantityArray.get(i);
            double price = prices.get(i);
            System.out.printf("%s %d %f\n",product, quantity, price);
            double total = quantity * price;
            totalAmount += total;
            orderReceipt.append(String.format("%-20s %-10d %-10.2f %-10.2f\n", product, quantity, price, total));
            
        }
        // Append total to JTextArea
        orderReceipt.append("----------------------------------------------\n");
        orderReceipt.append(String.format("%-40s %-10.2f\n", "Total Amount:", totalAmount));
    }
}
