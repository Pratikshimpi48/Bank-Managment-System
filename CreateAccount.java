package infobeans.banking.system;

import java.sql.Connection;
import java.awt.Font;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
import com.toedter.calendar.JDateChooser;
import java.sql.PreparedStatement;
import java.sql.Date;

public class CreateAccount extends JFrame implements ActionListener {

    JTextField name, fathersname, initialamt, depositamt, aadharno, mobileno, emailid, city, address, accountno, dateofbirth;
    JButton createacc, back;
    JLabel nameLabel, fatherLabel, initialamtLabel, depositLabel, aadharLabel, mobileLabel, emailLabel, genderLabel, cityLabel, dobLabel, addressLabel, accountLabel, passwordLabel;
    JComboBox<String> genderComboBox;
    JPasswordField passwordField;
    JDateChooser dobchooser;

    CreateAccount() {
        setTitle("Create An Account");
        setSize(800, 700);
        setLocation(240, 10);
        setVisible(true);
        setLayout(null);

        nameLabel = new JLabel("Name: ");
        nameLabel.setBounds(30, 30, 170, 30);
        nameLabel.setFont(new Font("Osward", Font.BOLD, 20));
        add(nameLabel);

        name = new JTextField();
        name.setBounds(200, 30, 190, 30);
        add(name);

        fatherLabel = new JLabel("Father's Name: ");
        fatherLabel.setBounds(400, 30, 180, 30);
        fatherLabel.setFont(new Font("Osward", Font.BOLD, 20));
        add(fatherLabel);

        fathersname = new JTextField();
        fathersname.setBounds(580, 30, 190, 30);
        add(fathersname);

        dobLabel = new JLabel("D.O.B: ");
        dobLabel.setBounds(30, 80, 170, 30);
        dobLabel.setFont(new Font("Osward", Font.BOLD, 20));
        add(dobLabel);

        dateofbirth = new JTextField();
        dateofbirth.setBounds(200, 80, 190, 30);
        add(dateofbirth);

        initialamtLabel = new JLabel("Initial Balance: ");
        initialamtLabel.setBounds(400, 80, 180, 30);
        initialamtLabel.setFont(new Font("Osward", Font.BOLD, 20));
        add(initialamtLabel);

        initialamt = new JTextField();
        initialamt.setBounds(580, 80, 190, 30);
        add(initialamt);

        depositLabel = new JLabel("Deposit amount: ");
        depositLabel.setBounds(30, 130, 170, 30);
        depositLabel.setFont(new Font("Osward", Font.BOLD, 20));
        add(depositLabel);

        depositamt = new JTextField();
        depositamt.setBounds(200, 130, 190, 30);
        add(depositamt);

        aadharLabel = new JLabel("Aadhar Number: ");
        aadharLabel.setBounds(400, 130, 180, 30);
        aadharLabel.setFont(new Font("Osward", Font.BOLD, 20));
        add(aadharLabel);

        aadharno = new JTextField();
        aadharno.setBounds(580, 130, 190, 30);
        add(aadharno);

        mobileLabel = new JLabel("Mobile Number: ");
        mobileLabel.setBounds(30, 180, 170, 30);
        mobileLabel.setFont(new Font("Osward", Font.BOLD, 20));
        add(mobileLabel);

        mobileno = new JTextField();
        mobileno.setBounds(200, 180, 190, 30);
        add(mobileno);

        emailLabel = new JLabel("Email ID: ");
        emailLabel.setBounds(400, 180, 180, 30);
        emailLabel.setFont(new Font("Osward", Font.BOLD, 20));
        add(emailLabel);

        emailid = new JTextField();
        emailid.setBounds(580, 180, 190, 30);
        add(emailid);

        genderLabel = new JLabel("Gender: ");
        genderLabel.setBounds(30, 230, 170, 30);
        genderLabel.setFont(new Font("Osward", Font.BOLD, 20));
        add(genderLabel);

        String[] genderOptions = {"Male", "Female", "Other"};
        genderComboBox = new JComboBox<>(genderOptions);
        genderComboBox.setBounds(200, 230, 190, 30);
        add(genderComboBox);

        cityLabel = new JLabel("City: ");
        cityLabel.setBounds(30, 280, 170, 30);
        cityLabel.setFont(new Font("Osward", Font.BOLD, 20));
        add(cityLabel);

        city = new JTextField();
        city.setBounds(200, 280, 190, 30);
        add(city);

        addressLabel = new JLabel("Address: ");
        addressLabel.setFont(new Font("Osward", Font.BOLD, 20));
        addressLabel.setBounds(400, 230, 180, 30);
        add(addressLabel);

        address = new JTextField();
        address.setBounds(580, 230, 190, 30);
        add(address);

        accountLabel = new JLabel("Account No: ");
        accountLabel.setBounds(400, 280, 150, 30);
        accountLabel.setFont(new Font("Osward", Font.BOLD, 20));
        add(accountLabel);

        accountno = new JTextField();
        accountno.setBounds(580, 280, 190, 30);
        add(accountno);

        passwordLabel = new JLabel("Password: ");
        passwordLabel.setBounds(30, 330, 150, 30);
        passwordLabel.setFont(new Font("Osward", Font.BOLD, 20));
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(200, 330, 190, 30);
        add(passwordField);

        createacc = new JButton("Create Account");
        createacc.setBounds(200, 440, 150, 40);
        add(createacc);
        createacc.addActionListener(this);

        back = new JButton("Back");
        back.setBounds(400, 440, 150, 40);
        back.addActionListener(this);
        add(back);

    }
//((JTextField) dobchooser.getDateEditor().getUiComponent()).getText();

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == back) {
            System.out.println("Back button clicked.");
            new Choice();
            dispose();
        }
        String Name = name.getText();
        String father = fathersname.getText();
        String dob = dateofbirth.getText();
        String initial = initialamt.getText();
        String deposit = depositamt.getText();
        String add = address.getText();
        String aadhar = aadharno.getText();
        String mobile = mobileno.getText();
        String email = emailid.getText();
        String City = city.getText();
        String account = accountno.getText();
        String gender = genderComboBox.getSelectedItem().toString();
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);
        
        if (Name.isEmpty() || containsInteger(Name) || father.isEmpty() || containsInteger(father)) {
            JOptionPane.showMessageDialog(this, "Name and Father's Name must be non-empty and should not contain integers.");
            return;
        }
        if (City.isEmpty() || containsInteger(City)) {
            JOptionPane.showMessageDialog(this, "City must be non-empty and should not contain integers.");
            return;
        }
        if (gender.isEmpty() || containsInteger(gender)) {
            JOptionPane.showMessageDialog(this, "Please select a valid Gender.");
            return;
        }
        if (deposit.isEmpty() || !deposit.matches("^[0-9]+$")) {
            JOptionPane.showMessageDialog(this, "Deposit amount must be a valid number without alphabetic characters.");
            return;
        }
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email id should not be empty");
            return;
        }
        if (aadhar.isEmpty() || !aadhar.matches("^[0-9]+$")) {
            JOptionPane.showMessageDialog(this, "Aadhar number should have alphanumeric values or should not be empty");
            return;
        }
        if (mobile.isEmpty() || !mobile.matches("^[0-9]+$")) {
            JOptionPane.showMessageDialog(this, "Mobile number should not be empty or alphanumeric values");
            return;
        }
        if (account.isEmpty() || !account.matches("^[0-9]+$")) {
            JOptionPane.showMessageDialog(this, "Account number should not be empty and must contain only numeric values.");
            return;
        }
        if (account.length() < 5) {
            JOptionPane.showMessageDialog(this, "Account number should have more than 4 digits.");
            return;
        }
        String addressText = address.getText();

        if (addressText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Address should not be empty or any negative values");
            return;
        }
//        if (password.isEmpty() || !password.matches(".*[a-zA-Z].*") || !password.matches(".*\\d.*") || password.length() < 5) {
//            JOptionPane.showMessageDialog(this, "Invalid password. It should contain at least one letter and one digit and be at least 4 characters long.");
//            return;
//        }

        try {
            double initialamt = Double.parseDouble(initial);
            if (initialamt < 1000 || !deposit.matches("^[0-9]+$")) {
                JOptionPane.showMessageDialog(this, "Initial Balance must be equal to or greater than 1000 rupees.");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Initial Balance must be a valid numeric value.");
            return;
        }
        try {
            int accountNumber = Integer.parseInt(account);
            if (accountNumber < 0) {
                JOptionPane.showMessageDialog(this, "Account number should not be a negative value.");
                return;
            } else {
                Conn c = new Conn();
                String query = "insert into Customer values(in)";
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Account number should be a valid integer.");
            return;
        }
        try {
            int count = 0;
            Conn c = new Conn();
            Connection connection = null;
            connection = c.getConnection();
            String s = "insert into customer(name,fathersname,DateofBirth,initialbalance,depositamount,address,mobileno,aadharno,email_id,city,gender,accountnum,password) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(s);
            pstmt.setString(1, Name);
            pstmt.setString(2, father);
            pstmt.setString(3, dob);
            pstmt.setString(4, initial);
            pstmt.setString(5, deposit);
            pstmt.setString(6, add);
            pstmt.setString(7, mobile);
            pstmt.setString(8, aadhar);
            pstmt.setString(9, email);
            pstmt.setString(10, City);
            pstmt.setString(11, gender);
            pstmt.setString(12, account);
            pstmt.setString(13, password);

            count = pstmt.executeUpdate();
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "Account created successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Account creation failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean containsInteger(String text) {
        return text.matches(".*\\d+.*");
    }

    public static void main(String args[]) {
        new CreateAccount();
    }
}
