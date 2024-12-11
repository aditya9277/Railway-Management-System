import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Login {
    private int userId;
    private String role;

    public Login() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());  // Set FlatLaf look and feel
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public void display() {
        JFrame frame = new JFrame("Railway System Login");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);  // Center the frame

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (authenticate(username, password)) {
                if (role.equals("admin")) {
                    AdminDashboard adminDashboard = new AdminDashboard();
                    adminDashboard.showAdminDashboard();  // Now it accepts the userId
                } else {
                    PassengerDashboard passengerDashboard = new PassengerDashboard(userId);
                    passengerDashboard.showPassengerDashboard();  // Now it accepts the userId
                }
                frame.dispose();  // Close the login window
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10));
        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);
        inputPanel.add(new JLabel());
        inputPanel.add(loginButton);

        panel.add(inputPanel);

        frame.add(panel);
        frame.setVisible(true);
    }

    private boolean authenticate(String username, String password) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Print the username and password to debug the input
            System.out.println("Attempting to login with username: " + username + " and password: " + password);

            String query = "SELECT id, role FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                userId = rs.getInt("id");
                role = rs.getString("role");
                System.out.println("Login successful. User ID: " + userId + ", User Type: " + role);
                return true;
            } else {
                System.out.println("Login failed: Invalid username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error during login. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login login = new Login();
            login.display();  // This is where the login screen is shown now
        });
    }
}
