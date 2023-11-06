package infobeans.banking.system;
import java.sql.*;

public class Conn {
    String url = "jdbc:mysql://localhost:3306/Infobeans_bank";
    String USERNAME = "root";
    String PASSWORD = "root";
    String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    public Connection getConnection() throws ClassNotFoundException, SQLException{
        Class.forName(DRIVER);
        Connection connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
        return connection;
    }
   
    public static void main(String args[]){
        
    }
}
