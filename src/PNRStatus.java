import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class PNRStatus {
    public void showPNRStatusScreen() {
        JFrame frame = new JFrame("PNR Status");
        frame.setSize(500, 300);

        JLabel pnrLabel = new JLabel("Enter PNR:");
        JTextField pnrField = new JTextField(15);
        JButton checkButton = new JButton("Check Status");

        JTextArea resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);

        checkButton.addActionListener(e -> checkPNRStatus(pnrField.getText(), resultArea));

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        inputPanel.add(pnrLabel);
        inputPanel.add(pnrField);
        inputPanel.add(new JLabel());
        inputPanel.add(checkButton);

        JPanel outputPanel = new JPanel();
        outputPanel.add(new JScrollPane(resultArea));

        frame.setLayout(new BorderLayout());
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(outputPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void checkPNRStatus(String pnr, JTextArea resultArea) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM bookings1 WHERE pnr = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, Long.parseLong(pnr));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                resultArea.setText(String.format("PNR: %d%nTrain ID: %d%n Train Name: %s%nDate: %s%nStatus: %s%n",
                        rs.getLong("pnr"),
                        rs.getInt("train_id"),
                        rs.getString("travel_date"),
                        rs.getString("status")));
            } else {
                resultArea.setText("No booking found for the entered PNR.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resultArea.setText("Error retrieving PNR status.");
        }
    }
}
