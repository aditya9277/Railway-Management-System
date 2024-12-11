// import javax.swing.*;
// import java.awt.*;
// import java.sql.*;

// public class Booking {
//     public void showBookingScreen(int userId) {
//         JFrame frame = new JFrame("Book a Ticket");
//         frame.setSize(500, 400);

//         JLabel trainLabel = new JLabel("Train ID:");
//         JLabel dateLabel = new JLabel("Travel Date (YYYY-MM-DD):");
//         JTextField trainField = new JTextField(15);
//         JTextField dateField = new JTextField(15);
//         JButton bookButton = new JButton("Book Ticket");

//         bookButton.addActionListener(e -> bookTicket(userId, trainField.getText(), dateField.getText(), frame));

//         JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
//         panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//         panel.add(trainLabel);
//         panel.add(trainField);
//         panel.add(dateLabel);
//         panel.add(dateField);
//         panel.add(new JLabel());
//         panel.add(bookButton);

//         frame.add(panel);
//         frame.setVisible(true);
//     }

//     private void bookTicket(int userId, String trainId, String travelDate, JFrame frame) {
//         try (Connection conn = DatabaseConnection.getConnection()) {
//             String query = "INSERT INTO bookings (user_id, train_id, travel_date, pnr, status) VALUES (?, ?, ?, ?, ?)";
//             PreparedStatement stmt = conn.prepareStatement(query);
//             stmt.setInt(1, userId);
//             stmt.setInt(2, Integer.parseInt(trainId));
//             stmt.setString(3, travelDate);
//             stmt.setLong(4, System.currentTimeMillis() % 1000000); // PNR generator
//             stmt.setString(5, "Confirmed");

//             stmt.executeUpdate();
//             JOptionPane.showMessageDialog(frame, "Ticket booked successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
//             frame.dispose();
//         } catch (SQLException e) {
//             e.printStackTrace();
//             JOptionPane.showMessageDialog(frame, "Error booking ticket.", "Error", JOptionPane.ERROR_MESSAGE);
//         }
//     }
// }

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Booking {
    public void showBookingScreen(int userId) {
        JFrame frame = new JFrame("Book a Ticket");
        frame.setSize(500, 400);

        JLabel trainLabel = new JLabel("Select Train:");
        JLabel dateLabel = new JLabel("Travel Date (YYYY-MM-DD):");
        JComboBox<String> trainComboBox = new JComboBox<>();
        JTextField dateField = new JTextField(15);
        JButton bookButton = new JButton("Book Ticket");

        // Populate the JComboBox with available trains
        loadTrains(trainComboBox);

        bookButton.addActionListener(
                e -> bookTicket(userId, trainComboBox.getSelectedItem().toString(), dateField.getText(), frame));

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(trainLabel);
        panel.add(trainComboBox);
        panel.add(dateLabel);
        panel.add(dateField);
        panel.add(new JLabel());
        panel.add(bookButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void loadTrains(JComboBox<String> trainComboBox) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT id, train_name FROM trains";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            // Add train names to the combo box
            while (rs.next()) {
                String trainName = rs.getString("train_name");
                trainComboBox.addItem(trainName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading trains.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void bookTicket(int userId, String trainName, String travelDate, JFrame frame) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Get the train ID based on the selected train name
            String trainQuery = "SELECT id FROM trains WHERE train_name = ?";
            PreparedStatement trainStmt = conn.prepareStatement(trainQuery);
            trainStmt.setString(1, trainName);
            ResultSet trainResult = trainStmt.executeQuery();

            if (!trainResult.next()) {
                JOptionPane.showMessageDialog(frame, "Train not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int trainId = trainResult.getInt("id");

            // Generate PNR and book the ticket
            String query = "INSERT INTO bookings1 (user_id, train_id, travel_date, pnr, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.setInt(2, trainId);
            stmt.setString(3, travelDate);
            stmt.setLong(4, System.currentTimeMillis() % 1000000); // PNR generator
            stmt.setString(5, "Confirmed");

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Ticket booked successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error booking ticket.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
