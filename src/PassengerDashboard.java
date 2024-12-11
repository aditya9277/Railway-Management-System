import javax.swing.*;
import java.awt.*;

public class PassengerDashboard {
    private final int userId;

    public PassengerDashboard(int userId) {
        this.userId = userId;
    }

    public void showPassengerDashboard() {
        JFrame frame = new JFrame("Passenger Dashboard");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton bookTicketButton = new JButton("Book Ticket");
        JButton checkPNRButton = new JButton("Check PNR Status");
        JButton orderFoodButton = new JButton("Order Food");
        JButton logoutButton = new JButton("Logout");

        bookTicketButton.addActionListener(e -> new Booking().showBookingScreen(userId));
        checkPNRButton.addActionListener(e -> new PNRStatus().showPNRStatusScreen());
        orderFoodButton.addActionListener(e -> new FoodOrder().showFoodOrderScreen());
        logoutButton.addActionListener(e -> {
            frame.dispose();
            new MainPage().showMainPage();
        });

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(bookTicketButton);
        panel.add(checkPNRButton);
        panel.add(orderFoodButton);
        panel.add(logoutButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}
