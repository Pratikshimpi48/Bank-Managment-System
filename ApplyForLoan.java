package infobeans.banking.system;

import java.awt.Font;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ApplyForLoan extends JFrame implements ActionListener {

    private String LoginAccountNumber;
    JLabel applyforloan;
    JLabel nemeLabel, accountNumberLabel, loanamountLabel, intrestRateLabel, monthLabel;
    JTextField nameField, accountNumberField, loanAmountField, intrestRateField, monthField;
    JButton applyButton, backButton;

    ApplyForLoan(String accountNumber) {
        this.LoginAccountNumber = accountNumber;
        setSize(800, 400);
        setLocation(250, 150);
        setLayout(null);
        setVisible(true);

        applyforloan = new JLabel("Apply For Loan");
        applyforloan.setBounds(280, 20, 200, 30);
        applyforloan.setFont(new Font("Osward", Font.BOLD, 25));
        add(applyforloan);

        nemeLabel = new JLabel("Name:");
        nemeLabel.setBounds(100, 100, 100, 30);
        add(nemeLabel);

        nameField = new JTextField();
        nameField.setBounds(220, 100, 150, 30);
        add(nameField);

        accountNumberLabel = new JLabel("Account Number:");
        accountNumberLabel.setBounds(400, 100, 150, 30);
        add(accountNumberLabel);

        accountNumberField = new JTextField(LoginAccountNumber);
        accountNumberField.setBounds(530, 100, 120, 30);
        accountNumberField.setEditable(false);
        add(accountNumberField);

        loanamountLabel = new JLabel("Loan Amount:");
        loanamountLabel.setBounds(100, 180, 100, 30);
        add(loanamountLabel);

        loanAmountField = new JTextField();
        loanAmountField.setBounds(220, 180, 150, 30);
        add(loanAmountField);

        intrestRateLabel = new JLabel("Intrest Rate (in %):");
        intrestRateLabel.setBounds(400, 180, 150, 30);
        add(intrestRateLabel);

        intrestRateField = new JTextField();
        intrestRateField.setBounds(530, 180, 120, 30);
        add(intrestRateField);

        monthLabel = new JLabel("Months:");
        monthLabel.setBounds(100, 260, 120, 30);
        add(monthLabel);

        monthField = new JTextField();
        monthField.setBounds(220, 260, 150, 30);
        add(monthField);

        applyButton = new JButton("Apply");
        applyButton.setBounds(250, 320, 100, 30);
        applyButton.addActionListener(this);
        add(applyButton);

        backButton = new JButton("Back");
        backButton.setBounds(400, 320, 100, 30);
        backButton.addActionListener((ActionListener) this);
        add(backButton);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == applyButton) {
            if (validateInput()) {
                String name = nameField.getText();
                double loanAmount = Double.parseDouble(loanAmountField.getText());
                double intrestRate = Double.parseDouble(intrestRateField.getText()) / 100.0;
                int month = Integer.parseInt(monthField.getText());

                double emi = calculateEmi(loanAmount, intrestRate, month);

                try {
                    Conn c = new Conn();
                    Connection connection = c.getConnection();
                    String query = "Insert into loanDetails(name,accountnumber,loanamount,intrestrate,month,emi) values(?,?,?,?,?,?)";
                    PreparedStatement pstmt = connection.prepareStatement(query);
                    pstmt.setString(1, name);
                    pstmt.setString(2, LoginAccountNumber);
                    pstmt.setDouble(3, loanAmount);
                    pstmt.setDouble(4, intrestRate);
                    pstmt.setInt(5, month);
                    pstmt.setDouble(6, emi);
                    pstmt.executeUpdate();
                    pstmt.close();
                    connection.close();
                    JOptionPane.showMessageDialog(null, "Loan application submitted successfully.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (ae.getSource() == backButton) {
            new UserFunctionality(LoginAccountNumber);
            dispose();
        }
    }

    private boolean validateInput() {
        String name = nameField.getText();
        String loanamountText = loanAmountField.getText();
        String intrestrRateText = intrestRateField.getText();
        String monthText = monthField.getText();

        if (name.isEmpty() || loanamountText.isEmpty() || intrestrRateText.isEmpty() || monthText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all the fields.");
            return false;
        }
        try {
            double loanAmount = Double.parseDouble(loanamountText);
            double interestRate = Double.parseDouble(intrestrRateText);
            int month = Integer.parseInt(monthText);

            if (loanAmount <= 0 || interestRate <= 0 || month <= 0) {
                JOptionPane.showMessageDialog(null, "Invalid input values. Please enter valid values.");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input format. Please enter valid numbers.");
            return false;
        }

        return true;
    }

    private double calculateEmi(double loanAmount, double intrestRate, int month) {
        double monthIntrest = (loanAmount / 12.0) / 100;
        double m = month * 12;
        double emi = (loanAmount + intrestRate) / m;
        return emi;
    }

    public static void main(String[] args) {
        new ApplyForLoan("123456");
    }
}
