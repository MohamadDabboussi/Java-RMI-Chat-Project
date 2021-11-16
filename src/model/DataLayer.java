package model;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.management.resource.ResourceType;

public class DataLayer {

    public String url = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;integratedSecurity=true;databaseName=Whatsapp";
    public Connection conn;
    public boolean IsValid = false;

    public DataLayer() {
        VerifyConnection();
    }

    private void VerifyConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url);
            conn.close();
            IsValid = true;
        } catch (Exception e) {
            IsValid = false;
        }
    }

    public int ExecuteActionCommand(String CommandText) {
        int rowsAffected = 0;

        if ((IsValid) && (CommandText.length() > 0)) {
            try {
                conn = DriverManager.getConnection(url);
            } catch (Exception e) {
                System.out.println("Connection to database failed!");
            }
            try {
                PreparedStatement statement = conn.prepareStatement(CommandText);
                rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("A new user was inserted successfully!");
                }
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataLayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rowsAffected;
    }
}
