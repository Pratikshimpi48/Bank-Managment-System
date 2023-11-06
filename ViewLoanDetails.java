package infobeans.banking.system;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewLoanDetails extends JFrame implements ActionListener {
    private String loggedInAccountNumber;
    private JTextArea loanDetailsTextArea;
    private JButton backButton;

    public ViewLoanDetails(String accountNumber) {
        this.loggedInAccountNumber = accountNumber;
        setTitle("Loan Account Details");
        setSize(800, 400);
        setLocation(250, 150);
        setLayout(null);
        setVisible(true);

        loanDetailsTextArea = new JTextArea();
        loanDetailsTextArea.setBounds(50, 50, 700, 200);
        loanDetailsTextArea.setEditable(false);
        add(loanDetailsTextArea);

        backButton = new JButton("Back");
        backButton.setBounds(350, 280, 100, 30);
        backButton.addActionListener(this);
        add(backButton);

        try {
            fetchAndDisplayLoanDetails();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == backButton) {
            new UserFunctionality(loggedInAccountNumber);
            dispose();
        }
    }

    private void fetchAndDisplayLoanDetails() throws SQLException {
        try {
            Conn c = new Conn();
            Connection connection = c.getConnection();
            String query = "SELECT * FROM loanDetails WHERE accountnumber = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, loggedInAccountNumber);
            ResultSet rs = pstmt.executeQuery();

            StringBuilder loanDetails = new StringBuilder();

            while (rs.next()) {
                loanDetails.append("Name: ").append(rs.getString("name")).append("\n");
                loanDetails.append("Loan Amount: $").append(rs.getDouble("loanamount")).append("\n");
                loanDetails.append("Interest Rate: ").append(rs.getDouble("intrestrate")).append("%\n");
                loanDetails.append("Months: ").append(rs.getInt("month")).append("\n");
                loanDetails.append("EMI: $").append(rs.getDouble("emi")).append("\n\n");
            }

            loanDetailsTextArea.setText(loanDetails.toString());

            rs.close();
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ViewLoanDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        new ViewLoanDetails("123456"); // Replace with the actual account number
    }
}
