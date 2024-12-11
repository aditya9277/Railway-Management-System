import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Register {
    public void showRegisterScreen() {
        JFrame frame = new JFrame("Register");
        frame.setSize(500, 400);

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel emailLabel = new JLabel("Email:");
        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);
        JTextField emailField = new JTextField(15);
        JLabel roleLabel = new JLabel("Role:");
        String[] roles = {"passenger", "admin"};
        JComboBox<String> roleComboBox = new JComboBox<>(roles);  // Dropdown for role selection
        JButton registerButton = new JButton("Register");

        registerButton.addActionListener(e -> registerUser(usernameField.getText(), new String(passwordField.getPassword()), emailField.getText(), (String) roleComboBox.getSelectedItem(), frame));

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(roleLabel);
        panel.add(roleComboBox);
        panel.add(new JLabel());
        panel.add(registerButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void registerUser(String username, String password, String email, String role, JFrame frame) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);  // You might want to hash the password
            stmt.setString(3, email);
            stmt.setString(4, role);  // Insert the role selected by the user

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(frame, "User registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                new MainPage().showMainPage();  // Redirect to main page
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error registering user.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
