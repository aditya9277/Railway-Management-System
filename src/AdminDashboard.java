import javax.swing.*;
import java.awt.*;

public class AdminDashboard {
    
    public void showAdminDashboard() {
        JFrame frame = new JFrame("Admin Dashboard");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton manageTrainsButton = new JButton("Manage Trains");
        JButton viewBookingsButton = new JButton("View Bookings");
        JButton logoutButton = new JButton("Logout");

        manageTrainsButton.addActionListener(e -> new TrainManager().manageTrains());
        viewBookingsButton.addActionListener(e -> new BookingViewer().viewBookings());
        logoutButton.addActionListener(e -> {
            frame.dispose();
            new Login().display();
        });

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(manageTrainsButton);
        panel.add(viewBookingsButton);
        panel.add(logoutButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}
