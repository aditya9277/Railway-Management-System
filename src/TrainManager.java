import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class TrainManager {
    public void manageTrains() {
        JFrame frame = new JFrame("Manage Trains");
        frame.setSize(500, 400);

        JTextArea textArea = new JTextArea(20, 40);
        textArea.setEditable(false);

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM trains";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%-20s %-20s%n", "Train Name", "Source-Destination"));
            sb.append("------------------------------------------------------\n");

            while (rs.next()) {
                sb.append(String.format("%-20s %-20s%n",

                        rs.getString("train_name"),
                        rs.getString("source") + "-" + rs.getString("destination")));
            }

            textArea.setText(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            textArea.setText("Error retrieving train data.");
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
