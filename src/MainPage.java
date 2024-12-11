import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class MainPage {

    public static void main(String[] args) {
        // Set the FlatLaf Look and Feel
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Launch the application
        SwingUtilities.invokeLater(() -> new MainPage().showMainPage());
    }

    public void showMainPage() {
        JFrame frame = new JFrame("Main Page");
        frame.setSize(500, 400);

        // Create buttons with FlatLaf style
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginButton.setFocusPainted(false);
        registerButton.setFocusPainted(false);

        loginButton.addActionListener(e -> {
            frame.dispose();
            new Login().display();  // Show Login screen
        });

        registerButton.addActionListener(e -> {
            frame.dispose();
            new Register().showRegisterScreen();  // Show Register screen
        });

        // Style the buttons using FlatLaf theme
        loginButton.putClientProperty("JButton.buttonType", "round");
        registerButton.putClientProperty("JButton.buttonType", "round");

        // Create a panel with a grid layout for buttons
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(loginButton);
        panel.add(registerButton);

        // Add panel to the frame
        frame.add(panel);

        // Set default close operation and make the frame visible
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
