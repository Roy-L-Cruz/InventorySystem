package INVENTORY;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connections {
    
    private Connection con;
    private Statement st;
    
    public static final String DbName = "inventory_data";
    public static final String DbDriver = "com.mysql.cj.jdbc.Driver";
    public static final String DbUrl = "jdbc:mysql://localhost:3306/"+DbName;
    private static final String DbUsername = "root";
    private static final String DbPassword = "";
    
    public void connectToDatabase() throws SQLException {
        try {
            Class.forName(DbDriver);
            
            con = DriverManager.getConnection(DbUrl, DbUsername, DbPassword);
            st = con.createStatement();
//            if (con != null) {
//                System.out.println("Connection established");
//            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Connections.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Connection getConnection() throws SQLException {
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory_data", "root", "");
        return con;
    }
    
    public Statement getStatement() throws SQLException {
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory_data", "root", "");
        st = con.createStatement();
        return st;
    }
    
}