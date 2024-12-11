import javax.swing.*;
import java.sql.*;

public class BookingViewer {
    public void viewBookings() {
        JFrame frame = new JFrame("View All Bookings");
        frame.setSize(500, 400);

        JTextArea textArea = new JTextArea(20, 40);
        textArea.setEditable(false);

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT b.id AS booking_id, u.username, t.train_name, b.pnr, b.status " +
                           "FROM bookings1 b " +
                           "JOIN users u ON b.user_id = u.id " +
                           "JOIN trains t ON b.train_id = t.id";

            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%-10s %-15s %-20s %-15s %-10s%n", "Booking ID", "Username", "Train Name", "PNR", "Status"));
            sb.append("---------------------------------------------------------------\n");

            while (rs.next()) {
                sb.append(String.format("%-10d %-15s %-20s %-15d %-10s%n",
                        rs.getInt("booking_id"),
                        rs.getString("username"),
                        rs.getString("train_name"),
                        rs.getLong("pnr"),
                        rs.getString("status")));
            }

            textArea.setText(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            textArea.setText("Error retrieving bookings.");
        }

        JScrollPane scrollPane = new JScrollPane(textArea);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> frame.dispose());

        JPanel panel = new JPanel();
        panel.add(scrollPane);
        panel.add(backButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}
