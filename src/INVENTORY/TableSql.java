package INVENTORY;

//import java.awt.Component;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JComboBox;
//import javax.swing.table.TableCellRenderer;

public class TableSql {
    
    private DefaultTableModel tableModel;
    private List<String> excludedColumns;
    private List<String> excludedOrderColumns;
    
    public TableSql() {
        excludedColumns = new ArrayList<>();
        excludedColumns.add("cost_price");
        excludedColumns.add("supplier");
        excludedColumns.add("minimum_stock");
        excludedColumns.add("shelf");
        excludedColumns.add("sale");
        
        excludedOrderColumns = new ArrayList<>();
        excludedOrderColumns.add("email_order");
        excludedOrderColumns.add("location_order");
    }
    
    public void search(JTextField searchField, JComboBox categoryField, JTable table) {
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            } 
        };
        Connections connect = new Connections();   
        String categoryText = (String) categoryField.getSelectedItem();
        String searchText = searchField.getText();     
        String query = "SELECT * FROM inventory WHERE product LIKE '%" + searchText + "%' OR category = '" + categoryText + "'";      
        try {
            ResultSet resultSet = connect.getStatement().executeQuery(query);
            // Retrieve column names
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                String columnName = metaData.getColumnName(i + 1);
                if (!excludedColumns.contains(columnName)) {
                    tableModel.addColumn(columnName);
                }   
            }
            // Populate table with data
            while (resultSet.next()) {
                Object[] rowData = new Object[tableModel.getColumnCount()];
                int index = 0;
                for (int i = 0; i < columnCount; i++) {
                    String columnName = metaData.getColumnName(i + 1);
                    if (!excludedColumns.contains(columnName)) {
                        rowData[index++] = resultSet.getObject(i + 1);
                    }
                }
                tableModel.addRow(rowData);
            }
            // Set table model to the provided table
            table.setModel(tableModel);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void removeProduct(String productName) {
        // Connection to the database
        Connections connect = new Connections();
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            } 
        };
        // SQL query to remove the product
        String removeQuery = "DELETE FROM inventory WHERE product = ?";
        try (Connection connection = connect.getConnection();
             PreparedStatement statement = connection.prepareStatement(removeQuery)) {
            // Set the product parameter
            statement.setString(1, productName);                 
            // Execute the delete operation
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product " + productName + " removed successfully.");
            } else {
                System.out.println("Product " + productName + " not found in inventory.");
            }
        } catch (SQLException e) {
            System.err.println("Error removing product: " + e.getMessage());
        }
    }

    public void populateUserTable(JTable table) {
        Connections connect = new Connections();
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            } 
        };
        String query = "SELECT * FROM inventory";
        
        try (ResultSet resultSet = connect.getStatement().executeQuery(query)) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Populate column names
            for (int i = 0; i < columnCount; i++) {
                String columnName = metaData.getColumnName(i + 1);
                if (!excludedColumns.contains(columnName)) {
                    tableModel.addColumn(columnName);
                }
            }

            // Populate table with data
            while (resultSet.next()) {
                Object[] rowData = new Object[tableModel.getColumnCount()];
                int index = 0;
                for (int i = 0; i < columnCount; i++) {
                    String columnName = metaData.getColumnName(i + 1);
                    if (!excludedColumns.contains(columnName)) {
                        rowData[index++] = resultSet.getObject(i + 1);
                    }
                }
                tableModel.addRow(rowData);
            }
            // Set table model to the provided table
            table.setModel(tableModel);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void populateOrderUser(JTable table, String email) {
        Connections connect = new Connections();
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            } 
        };
        String query = "SELECT * FROM order_placement WHERE email_order = ?";
 
        try (Connection connection = connect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Set the email parameter
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                // Initialize table model and set column names
                tableModel = new DefaultTableModel();
                tableModel.addColumn("Order Number");
                tableModel.addColumn("Product");
                tableModel.addColumn("Quantity");
                tableModel.addColumn("Total");
                tableModel.addColumn("Order Status");
                // Populate table with data
                while (resultSet.next()) {
                    Object[] rowData = new Object[columnCount];
                    int index = 0;
                    for (int i = 0; i < columnCount; i++) {
                        String columnName = metaData.getColumnName(i + 1);
                        if (!excludedOrderColumns.contains(columnName)) {
                            rowData[index++] = resultSet.getObject(i + 1);
                        }
                    }
                    tableModel.addRow(rowData);
                }
                // Set table model to the provided table
                table.setModel(tableModel);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
//    public void populateOrderAdmin(JTable table) {
//        Connections connect = new Connections();
//        tableModel = new DefaultTableModel();
//        String query = "SELECT * FROM order_placement";
//        
//        try (ResultSet resultSet = connect.getStatement().executeQuery(query)) {
//            ResultSetMetaData metaData = resultSet.getMetaData();
//            int columnCount = metaData.getColumnCount();
//            // Populate column names
//            tableModel.addColumn("Order Number");
//            tableModel.addColumn("Email");
//            tableModel.addColumn("Product");
//            tableModel.addColumn("Quantity");
//            tableModel.addColumn("Total");
//            tableModel.addColumn("Location");
//            tableModel.addColumn("Order Status");
//            // Populate table with data
//            while (resultSet.next()) {
//                Object[] rowData = new Object[tableModel.getColumnCount()];
//                int index = 0;
//                for (int i = 0; i < columnCount; i++) {
//                    String columnName = metaData.getColumnName(i + 1);
//                    rowData[index++] = resultSet.getObject(i + 1);
//                }
//                tableModel.addRow(rowData);
//            }
//            // Set table model to the provided table
//            table.setModel(tableModel);
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//    }
    
    public void populateOrderAdmin(JTable table, String delivery_refund) {
        Connections connect = new Connections();
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            } 
        };
        String query = "SELECT * FROM order_placement WHERE order_status = ?";
        try (PreparedStatement statement = connect.getConnection().prepareStatement(query)) {
            // Set the parameter for the SQL query
            statement.setString(1, delivery_refund);        
            try (ResultSet resultSet = statement.executeQuery()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                // Populate column names
                tableModel.addColumn("Order Number");
                tableModel.addColumn("Email");
                tableModel.addColumn("Product");
                tableModel.addColumn("Quantity");
                tableModel.addColumn("Total");
                tableModel.addColumn("Location");
                tableModel.addColumn("Order Status");
                // Populate table with data
                while (resultSet.next()) {
                    Object[] rowData = new Object[tableModel.getColumnCount()];
                    for (int i = 0; i < columnCount; i++) {
                        rowData[i] = resultSet.getObject(i + 1);
                    }
                    tableModel.addRow(rowData);
                }
                // Set table model to the provided table
                table.setModel(tableModel);    
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void adminSearch(JTextField searchField, JTable table) {
        Connections connect = new Connections();
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            } 
        };
        String searchText = searchField.getText();
        String query = "SELECT * FROM inventory WHERE product LIKE '%" + searchText + "%'";

        try {
            ResultSet resultSet = connect.getStatement().executeQuery(query);
            // Retrieve column names
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                columnNames[i] = metaData.getColumnName(i + 1);
            }
            tableModel.setColumnIdentifiers(columnNames);
            // Populate table with data
            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    rowData[i] = resultSet.getObject(i + 1);
                }
                tableModel.addRow(rowData);
            }
            // Set table model to the provided table
            table.setModel(tableModel);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void populateTable(JTable table) {
        Connections connect = new Connections();     
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            } 
        };
        String query = "SELECT * FROM inventory"; 
        try (ResultSet resultSet = connect.getStatement().executeQuery(query)) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            // Populate column names
            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(metaData.getColumnName(i));
            }
            // Populate table with data
            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    rowData[i] = resultSet.getObject(i + 1);
                }
                tableModel.addRow(rowData);
            }
            // Set table model to the provided table
            table.setModel(tableModel);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void populateAccTable(JTable table) {
        Connections connect = new Connections();      
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            } 
        };
        String query = "SELECT * FROM account_info"; 
        try (ResultSet resultSet = connect.getStatement().executeQuery(query)) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            // Populate column names
            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(metaData.getColumnName(i));
            }
            // Populate table with data
            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    rowData[i] = resultSet.getObject(i + 1);
                }
                tableModel.addRow(rowData);
            }
            // Set table model to the provided table
            table.setModel(tableModel);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void getInfo(JTextField info, int accNum, String columnName) {
        Connections connect = new Connections(); 
        String query = "SELECT " + columnName + " FROM account_info WHERE acc_number = ?";
        try (Connection connection = connect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, accNum);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Object columnValue = resultSet.getObject(1);
                    if (columnValue == null) {
                        info.setText("none");
                    } else {
                        info.setText(columnValue.toString());
                    }
                } else {
                    info.setText("none");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
