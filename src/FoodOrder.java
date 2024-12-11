import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class FoodOrder {
    public void showFoodOrderScreen() {
        JFrame frame = new JFrame("Order Food");
        frame.setSize(500, 400);

        JLabel pnrLabel = new JLabel("Enter PNR:");
        JLabel foodLabel = new JLabel("Select Food Item:");
        JTextField pnrField = new JTextField(15);
        
        // Create a combo box with dummy food items
        String[] foodItems = {"Paneer Butter Masala", "Butter Chicken", "Dal Makhani", "Chole Bhature", "Biryani", "Aloo Gobi", "Samosa"};
        JComboBox<String> foodComboBox = new JComboBox<>(foodItems);
        
        JButton orderButton = new JButton("Place Order");

        orderButton.addActionListener(e -> placeFoodOrder(pnrField.getText(), (String) foodComboBox.getSelectedItem(), frame));

        // Create a panel to arrange the components
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(pnrLabel);
        panel.add(pnrField);
        panel.add(foodLabel);
        panel.add(foodComboBox);
        panel.add(new JLabel()); // Empty space for layout
        panel.add(orderButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void placeFoodOrder(String pnr, String foodItem, JFrame frame) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Step 1: Get the booking_id using the provided PNR
            String bookingQuery = "SELECT id FROM bookings1 WHERE pnr = ?";
            PreparedStatement bookingStmt = conn.prepareStatement(bookingQuery);
            bookingStmt.setString(1, pnr);
            ResultSet bookingResult = bookingStmt.executeQuery();

            if (!bookingResult.next()) {
                JOptionPane.showMessageDialog(frame, "PNR not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int bookingId = bookingResult.getInt("id");

            // Step 2: Insert food order into food_orders table
            String orderQuery = "INSERT INTO food_orders (booking_id, food_item, quantity, total_price) VALUES (?, ?, ?, ?)";
            PreparedStatement orderStmt = conn.prepareStatement(orderQuery);

            // Set food order details
            orderStmt.setInt(1, bookingId);
            orderStmt.setString(2, foodItem);
            orderStmt.setInt(3, 1); // Assuming a quantity of 1 for simplicity
            orderStmt.setDouble(4, 100.00); // Assuming a fixed price for simplicity. You can modify this.

            // Execute the order insertion
            orderStmt.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Food order placed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error placing food order.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
