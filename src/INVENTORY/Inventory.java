
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
    
    public void updateInfo(JTextField fInfo, JTextField mInfo, JTextField lInfo, JTextField eInfo, JTextField cInfo, JTextField gInfo, String user, int userNum) {
        // Connection to the database
        Connections connect = new Connections();
        // SQL query for updating the user's info
        String query = "UPDATE account_info SET acc_email = ?, first_name = ?, middle_name = ?, last_name = ?, contact = ?, gender = ? WHERE acc_number = ?";
        // Set the data to variables
        String email = eInfo.getText();
        String first = fInfo.getText();
        String middle = mInfo.getText();
        String last = lInfo.getText();
        String contact = cInfo.getText();
        String gender = gInfo.getText();
        try (Connection connection = connect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            // Set parameters, handling empty fields by inserting NULL
            statement.setString(1, email.isEmpty() ? user : email);
            statement.setString(2, first.isEmpty() ? null : first);
            statement.setString(3, middle.isEmpty() ? null : middle);
            statement.setString(4, last.isEmpty() ? null : last);
            statement.setString(5, contact.isEmpty() ? null : contact);
            statement.setString(6, gender.isEmpty() ? null : gender);
            statement.setInt(7, userNum);
            // Execute the query
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Update complete.");
            } else {
                System.out.println("No user found with the specified email.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating Info: " + e.getMessage());
        }
    }
    
    public void addAccount(JTextField emailField, JTextField passField, JTextField firstField, JTextField middleField, JTextField lastField, JTextField contactField, JTextField genderField) {
        // Connection to the database
        Connections connect = new Connections();
        // SQL query for inserting a new account
        String query = "INSERT INTO account_info (acc_email, acc_password, first_name, middle_name, last_name, contact, gender) VALUES (?, ?, ?, ?, ?, ?, ?)";
        // Set the data to variables
        String email = emailField.getText();
        String password = passField.getText();
        String firstName = firstField.getText();
        String middleName = middleField.getText();
        String lastName = lastField.getText();
        String contact = contactField.getText();
        String gender = genderField.getText();
        try (Connection connection = connect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            // Set parameters, handling empty fields by inserting NULL
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3, firstName.isEmpty() ? null : firstName);
            statement.setString(4, middleName.isEmpty() ? null : middleName);
            statement.setString(5, lastName.isEmpty() ? null : lastName);
            statement.setString(6, contact.isEmpty() ? null : contact);
            statement.setString(7, gender.isEmpty() ? null : gender);
            // Execute the query
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Account added successfully.");
            } else {
                System.out.println("Failed to add account.");
            }
        } catch (SQLException e) {
            System.err.println("Error adding account: " + e.getMessage());
        }
    }
    
    public void deleteAccount(int accNumber, String accEmail) {
        // Connection to the database
        Connections connect = new Connections();
        // SQL query for deleting an account
        String query = "DELETE FROM account_info WHERE acc_number = ? AND acc_email = ?";
        try (Connection connection = connect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            // Set parameters
            statement.setInt(1, accNumber);
            statement.setString(2, accEmail);
            // Execute the query
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Account deleted successfully.");
            } else {
                System.out.println("No account found with the specified account number and email.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting account: " + e.getMessage());
        }
    }
    
    public void updatePassword(String newPassword, String confirmPassword, int accNum) {
        // Check if the passwords match
        if (!newPassword.equals(confirmPassword)) {
            System.out.println("Passwords do not match.");
            return;
        }
        // Connection to the database
        Connections connect = new Connections();
        // SQL query for updating the password
        String query = "UPDATE account_info SET acc_password = ? WHERE acc_number = ?";
        try (Connection connection = connect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            // Set parameters
            statement.setString(1, newPassword);
            statement.setInt(2, accNum);
            // Execute the query
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Password updated successfully.");
            } else {
                System.out.println("No account found with the specified account number.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating password: " + e.getMessage());
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
    
    public void orderStatus(String status, String order_num) {
        // Connection to the database
        Connections connect = new Connections();
        // SQL query for updating the user's info
        String query = "UPDATE order_management SET order_status = ? WHERE acc_number = ?";
        try (Connection connection = connect.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
                // Set parameters
                statement.setString(1, status);
                statement.setInt(2, Integer.parseInt(order_num));
                // Execute the query
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Order status updated successfully.");
                } else {
                    System.out.println("No order found with the given account number.");
                }
        } catch (SQLException e) {
            System.err.println("Error updating order status: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid account number format: " + e.getMessage());
        }
    }
    
    public void settings(String set, int value, int productCode) {
        // Connection to the database
        Connections connect = new Connections();
        // SQL query for updating the user's info
        String query = "UPDATE inventory SET "+set +" = ? WHERE code = ?";
        try (Connection connection = connect.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
                // Set parameters
                statement.setInt(1, value);
                statement.setInt(2, productCode);
                // Execute the query
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Order status updated successfully.");
                } else {
                    System.out.println("Invalid settings");
                }
        } catch (SQLException e) {
            System.err.println("Error updating: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid: " + e.getMessage());
        }
    }
}
