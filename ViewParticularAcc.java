package infobeans.banking.system;

import javax.swing.*;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewParticularAcc extends JFrame implements ActionListener {
    private JFrame prev;
    private String accountNumber;

    JLabel nameLabel, fatherLabel, dobLabel, initialamtLabel, depositLabel, aadharLabel, mobileLabel, emailLabel, genderLabel, cityLabel, addressLabel, accountLabel;
    JLabel nameValueLabel, fatherValueLabel, dobValueLabel, initialamtValueLabel, depositValueLabel, aadharValueLabel, mobileValueLabel, emailValueLabel, genderValueLabel, cityValueLabel, addressValueLabel, accountValueLabel;

    JButton backButton;

    ViewParticularAcc(String accountNumber) {
        this.accountNumber = accountNumber;
        setTitle("View Customer Details");
        setSize(800, 600);
        setLocation(240, 10);
        setVisible(true);
        setLayout(null);

        nameLabel = new JLabel("Name: ");
        nameLabel.setBounds(30, 50, 170, 30);
        nameLabel.setFont(new Font("Osward", Font.BOLD, 15));
        add(nameLabel);

        fatherLabel = new JLabel("Father's Name: ");
        fatherLabel.setBounds(400, 50, 170, 30);
        fatherLabel.setFont(new Font("Osward", Font.BOLD, 15));
        add(fatherLabel);

        dobLabel = new JLabel("D.O.B: ");
        dobLabel.setBounds(30, 100, 170, 30);
        dobLabel.setFont(new Font("Osward", Font.BOLD, 15));
        add(dobLabel);

        initialamtLabel = new JLabel("Initial Balance: ");
        initialamtLabel.setBounds(400, 100, 170, 30);
        initialamtLabel.setFont(new Font("Osward", Font.BOLD, 15));
        add(initialamtLabel);

        depositLabel = new JLabel("Deposit amount: ");
        depositLabel.setBounds(30, 150, 170, 30);
        depositLabel.setFont(new Font("Osward", Font.BOLD, 15));
        add(depositLabel);

        aadharLabel = new JLabel("Aadhar Number: ");
        aadharLabel.setBounds(400, 150, 170, 30);
        aadharLabel.setFont(new Font("Osward", Font.BOLD, 15));
        add(aadharLabel);

        mobileLabel = new JLabel("Mobile Number: ");
        mobileLabel.setBounds(30, 200, 170, 30);
        mobileLabel.setFont(new Font("Osward", Font.BOLD, 15));
        add(mobileLabel);

        emailLabel = new JLabel("Email ID: ");
        emailLabel.setBounds(400, 200, 170, 30);
        emailLabel.setFont(new Font("Osward", Font.BOLD, 15));
        add(emailLabel);

        genderLabel = new JLabel("Gender: ");
        genderLabel.setBounds(30, 250, 170, 30);
        genderLabel.setFont(new Font("Osward", Font.BOLD, 15));
        add(genderLabel);

        cityLabel = new JLabel("City: ");
        cityLabel.setBounds(400, 250, 170, 30);
        cityLabel.setFont(new Font("Osward", Font.BOLD, 15));
        add(cityLabel);

        addressLabel = new JLabel("Address: ");
        addressLabel.setBounds(30, 300, 170, 30);
        addressLabel.setFont(new Font("Osward", Font.BOLD, 15));
        add(addressLabel);

        accountLabel = new JLabel("Account Number: ");
        accountLabel.setBounds(400, 300, 170, 30);
        accountLabel.setFont(new Font("Osward", Font.BOLD, 15));
        add(accountLabel);

        fetchDataFromDatabase(accountNumber);

        backButton = new JButton("Back");
        backButton.setBounds(300, 400, 100, 40);
        add(backButton);
        backButton.addActionListener(this);
    }

    private void fetchDataFromDatabase(String accountNumber) {
        try {
            Conn c = new Conn();
            Connection connection = c.getConnection();
            String query = "SELECT * FROM Customer WHERE accountnum = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, accountNumber);

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                nameValueLabel = new JLabel(resultSet.getString("name"));
                nameValueLabel.setBounds(200, 50, 190, 30);
                add(nameValueLabel);

                fatherValueLabel = new JLabel(resultSet.getString("fathersname"));
                fatherValueLabel.setBounds(600, 50, 190, 30);
                add(fatherValueLabel);

                dobValueLabel = new JLabel(resultSet.getString("DateofBirth"));
                dobValueLabel.setBounds(200, 100, 190, 30);
                add(dobValueLabel);

                initialamtValueLabel = new JLabel(resultSet.getString("initialbalance"));
                initialamtValueLabel.setBounds(600, 100, 190, 30);
                add(initialamtValueLabel);

                depositValueLabel = new JLabel(resultSet.getString("depositamount"));
                depositValueLabel.setBounds(200, 150, 190, 30);
                add(depositValueLabel);

                aadharValueLabel = new JLabel(resultSet.getString("aadharno"));
                aadharValueLabel.setBounds(600, 150, 190, 30);
                add(aadharValueLabel);

                mobileValueLabel = new JLabel(resultSet.getString("mobileno"));
                mobileValueLabel.setBounds(200, 200, 190, 30);
                add(mobileValueLabel);

                emailValueLabel = new JLabel(resultSet.getString("email_id"));
                emailValueLabel.setBounds(600, 200, 190, 30);
                add(emailValueLabel);

                genderValueLabel = new JLabel(resultSet.getString("gender"));
                genderValueLabel.setBounds(200, 250, 190, 30);
                add(genderValueLabel);

                cityValueLabel = new JLabel(resultSet.getString("city"));
                cityValueLabel.setBounds(600, 250, 190, 30);
                add(cityValueLabel);

                addressValueLabel = new JLabel(resultSet.getString("address"));
                addressValueLabel.setBounds(200, 300, 190, 30);
                add(addressValueLabel);

                accountValueLabel = new JLabel(resultSet.getString("accountnum"));
                accountValueLabel.setBounds(600, 300, 190, 30);
                add(accountValueLabel);
            } else {
                JOptionPane.showMessageDialog(this, "Customer not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == backButton) {
            if (prev != null) {
                prev.setVisible(true);
            }
            dispose();
        }
        new AdminFunctionality();
    }

    public static void main(String[] args) {
        new ViewParticularAcc("your_account_number");
    }
}
