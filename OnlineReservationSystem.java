package Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class OnlineReservationSystem {

    private static Map<String, String> users = new HashMap<>();
    private static Map<String, Ticket> reservations = new HashMap<>();
    private static String loggedInUser;

    public static void main(String[] args) {
        // Predefined users (for demonstration)
        users.put("user1", "password1");
        users.put("user2", "password2");

        loadReservations(); // Load reservations from file
        showLoginForm();
    }

    private static void loadReservations() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("reservations.dat"))) {
            reservations = (Map<String, Ticket>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No reservations found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void saveReservations() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("reservations.dat"))) {
            oos.writeObject(reservations);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void showLoginForm() {
        JFrame frame = new JFrame("Login Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(220, 220, 220)); // Light gray background

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(15);
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        loginButton.setBackground(new Color(0, 123, 255)); // Bootstrap primary blue
        loginButton.setForeground(Color.WHITE);
        registerButton.setBackground(new Color(40, 167, 69)); // Bootstrap success green
        registerButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        registerButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder());
        registerButton.setBorder(BorderFactory.createEmptyBorder());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userLabel, gbc);

        gbc.gridx = 1;
        panel.add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passLabel, gbc);

        gbc.gridx = 1;
        panel.add(passField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Span across both columns
        panel.add(loginButton, gbc);
        
        gbc.gridy = 3;
        panel.add(registerButton, gbc);

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if (users.containsKey(username) && users.get(username).equals(password)) {
                loggedInUser = username;
                frame.dispose();
                showMainMenu();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> {
            frame.dispose();
            showRegistrationForm();
        });

        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void showRegistrationForm() {
        JFrame regFrame = new JFrame("Registration Form");
        regFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        regFrame.setSize(400, 300);
        regFrame.setLayout(new GridBagLayout());
        regFrame.getContentPane().setBackground(new Color(220, 220, 220)); // Light gray background

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(15);
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(15);
        JButton registerButton = new JButton("Register");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userLabel, gbc);

        gbc.gridx = 1;
        panel.add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passLabel, gbc);

        gbc.gridx = 1;
        panel.add(passField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Span across both columns
        panel.add(registerButton, gbc);

        registerButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(regFrame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (users.containsKey(username)) {
                JOptionPane.showMessageDialog(regFrame, "Username already exists. Please choose another.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                users.put(username, password);
                JOptionPane.showMessageDialog(regFrame, "Registration successful! You can now log in.");
                regFrame.dispose();
                showLoginForm();
            }
        });

        regFrame.add(panel);
        regFrame.setLocationRelativeTo(null);
        regFrame.setVisible(true);
    }

    private static void showMainMenu() {
        JFrame menuFrame = new JFrame("Main Menu");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(300, 300);
        menuFrame.setLayout(new GridLayout(5, 1, 10, 10));
        menuFrame.setLocationRelativeTo(null);
        menuFrame.getContentPane().setBackground(new Color(135, 206, 250)); // Sky blue

        JButton reserveButton = new JButton("Make a Reservation");
        JButton cancelButton = new JButton("Cancel a Reservation");
        JButton viewBookingsButton = new JButton("View Stored Bookings");
        JButton logoutButton = new JButton("Logout");

        // Set button colors
        reserveButton.setBackground(new Color(0, 123, 255)); // Bootstrap primary blue
        reserveButton.setForeground(Color.WHITE);
        cancelButton.setBackground(new Color(255, 0, 0)); // Bright red
        cancelButton.setForeground(Color.WHITE);
        viewBookingsButton.setBackground(new Color(40, 167, 69)); // Bootstrap success green
        viewBookingsButton.setForeground(Color.WHITE);
        logoutButton.setBackground(new Color(255, 215, 0)); // Gold
        logoutButton.setForeground(Color.BLACK);

        reserveButton.addActionListener(e -> showReservationForm());
        cancelButton.addActionListener(e -> showCancellationForm());
        viewBookingsButton.addActionListener(e -> showStoredBookings());
        logoutButton.addActionListener(e -> {
            loggedInUser = null;
            saveReservations(); // Save reservations on logout
            menuFrame.dispose();
            showLoginForm();
        });

        menuFrame.add(reserveButton);
        menuFrame.add(cancelButton);
        menuFrame.add(viewBookingsButton);
        menuFrame.add(logoutButton);
        menuFrame.setVisible(true);
    }

    private static void showStoredBookings() {
        StringBuilder bookings = new StringBuilder("Stored Bookings:\n\n");
        if (reservations.isEmpty()) {
            bookings.append("No bookings found.");
        } else {
            for (Ticket ticket : reservations.values()) {
                bookings.append("PNR: ").append(ticket.pnr)
                        .append(", Name: ").append(ticket.name)
                        .append(", Train: ").append(ticket.trainNumber)
                        .append(", Class: ").append(ticket.classType)
                        .append(", Date: ").append(ticket.date)
                        .append(", From: ").append(ticket.from)
                        .append(", To: ").append(ticket.to)
                        .append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, bookings.toString(), "Stored Bookings", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void showReservationForm() {
        JFrame reserveFrame = new JFrame("Reservation Form");
        reserveFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        reserveFrame.setSize(400, 350);
        reserveFrame.setLayout(new GridLayout(7, 2, 10, 10));
        reserveFrame.setLocationRelativeTo(null);
        reserveFrame.getContentPane().setBackground(new Color(255, 228, 196)); // Light peach

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel trainLabel = new JLabel("Train Number:");
        JTextField trainField = new JTextField();
        JLabel classLabel = new JLabel("Class Type:");
        JTextField classField = new JTextField();
        JLabel dateLabel = new JLabel("Date of Journey (yyyy-mm-dd):");
        JTextField dateField = new JTextField();
        JLabel fromLabel = new JLabel("From:");
        JTextField fromField = new JTextField();
        JLabel toLabel = new JLabel("To:");
        JTextField toField = new JTextField();
        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(0, 123, 255)); // Bootstrap primary blue
        submitButton.setForeground(Color.WHITE);

        reserveFrame.add(nameLabel);
        reserveFrame.add(nameField);
        reserveFrame.add(trainLabel);
        reserveFrame.add(trainField);
        reserveFrame.add(classLabel);
        reserveFrame.add(classField);
        reserveFrame.add(dateLabel);
        reserveFrame.add(dateField);
        reserveFrame.add(fromLabel);
        reserveFrame.add(fromField);
        reserveFrame.add(toLabel);
        reserveFrame.add(toField);
        reserveFrame.add(submitButton);

        submitButton.addActionListener(e -> {
            String name = nameField.getText();
            String trainNumber = trainField.getText();
            String classType = classField.getText();
            String date = dateField.getText();
            String from = fromField.getText();
            String to = toField.getText();

            if (name.isEmpty() || trainNumber.isEmpty() || classType.isEmpty() ||
                date.isEmpty() || from.isEmpty() || to.isEmpty()) {
                JOptionPane.showMessageDialog(reserveFrame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String pnr = generatePNR();
            Ticket ticket = new Ticket(name, trainNumber, classType, date, from, to, pnr);
            reservations.put(pnr, ticket);
            JOptionPane.showMessageDialog(reserveFrame, "Reservation successful! Your PNR is: " + pnr);
            reserveFrame.dispose();
        });

        reserveFrame.setVisible(true);
    }

    private static void showCancellationForm() {
        JFrame cancelFrame = new JFrame("Cancellation Form");
        cancelFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cancelFrame.setSize(300, 150);
        cancelFrame.setLayout(new GridLayout(3, 2, 10, 10));
        cancelFrame.setLocationRelativeTo(null);
        cancelFrame.getContentPane().setBackground(new Color(255, 228, 196)); // Light peach

        JLabel pnrLabel = new JLabel("Enter PNR:");
        JTextField pnrField = new JTextField();
        JButton cancelButton = new JButton("Cancel Ticket");
        cancelButton.setBackground(new Color(255, 0, 0)); // Bright red
        cancelButton.setForeground(Color.WHITE);

        cancelFrame.add(pnrLabel);
        cancelFrame.add(pnrField);
        cancelFrame.add(cancelButton);

        cancelButton.addActionListener(e -> {
            String pnr = pnrField.getText();
            Ticket ticket = reservations.remove(pnr);

            if (ticket != null) {
                JOptionPane.showMessageDialog(cancelFrame, "Cancellation successful for PNR: " + pnr);
            } else {
                JOptionPane.showMessageDialog(cancelFrame, "PNR not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            cancelFrame.dispose();
        });

        cancelFrame.setVisible(true);
    }

    private static String generatePNR() {
        return String.valueOf(System.currentTimeMillis()); // Simple PNR generation
    }

    private static class Ticket implements Serializable {
        String name;
        String trainNumber;
        String classType;
        String date;
        String from;
        String to;
        String pnr;

        Ticket(String name, String trainNumber, String classType, String date, String from, String to, String pnr) {
            this.name = name;
            this.trainNumber = trainNumber;
            this.classType = classType;
            this.date = date;
            this.from = from;
            this.to = to;
            this.pnr = pnr;
        }
    }
}
