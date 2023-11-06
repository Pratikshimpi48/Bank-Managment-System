package infobeans.banking.system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CheckBalance {

    private String accountNumber;

    CheckBalance(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        try {
            Conn c = new Conn();
            Connection connection = c.getConnection();
            String query = "SELECT depositamount FROM Customer WHERE accountnum = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("depositamount");
            } else {
                return 0.0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public static void main(String[] args) {
        
    }
}
