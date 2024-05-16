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
    
    public TableSql() {
        excludedColumns = new ArrayList<>();
        excludedColumns.add("cost_price");
        excludedColumns.add("supplier");
        excludedColumns.add("minimum_stock");
        excludedColumns.add("shelf");
        excludedColumns.add("sale");
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
        DefaultTableModel tableModel = new DefaultTableModel();
        
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
    
    public void adminSearch(JTextField searchField, JTable table) {
        tableModel = new DefaultTableModel();
        Connections connect = new Connections();

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
        DefaultTableModel tableModel = new DefaultTableModel();      
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
        DefaultTableModel tableModel = new DefaultTableModel();      
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
}
