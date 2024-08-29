package medicinedosetrackerjava;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Medicine Dose Tracker Application
 * 
 * @author Asus
 */
public class Medicinedosetrackerjava {

    private JFrame frame;
    private JPanel loginPanel, medPanel;
    private JTextField usernameField, medNameField, medDosageField, medFrequencyField;
    private JTable medicineTable;
    private DefaultTableModel tableModel;
    private Map<String, List<Map<String, String>>> users = new HashMap<>();
    private String currentUser;

    public Medicinedosetrackerjava() {
        frame = new JFrame("Medicine Reminder App");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new CardLayout());

        loginPanel = new JPanel();
        medPanel = new JPanel();

        frame.add(loginPanel, "login");
        frame.add(medPanel, "medicines");

        createLoginPanel();
        loadUserData();
    }

    private void createLoginPanel() {
        loginPanel.setLayout(new GridLayout(4, 1, 10, 10));
        JLabel loginLabel = new JLabel("Login or Register", SwingConstants.CENTER);
        loginLabel.setFont(new Font("Helvetica", Font.BOLD, 14));
        loginPanel.add(loginLabel);

        usernameField = new JTextField();
        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameField);

        JButton loginButton = new JButton("Login / Register");
        loginButton.addActionListener(e -> loginOrRegister());
        loginPanel.add(loginButton);
    }

    private void loginOrRegister() {
        String username = usernameField.getText();
        if (users.containsKey(username)) {
            currentUser = username;
            switchToMedicinePanel();
        } else {
            int option = JOptionPane.showConfirmDialog(frame, "User not found. Register as a new user?", "Registration", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                registerUser(username);
                switchToMedicinePanel();
            }
        }
    }

    private void registerUser(String username) {
        users.put(username, new ArrayList<>());
        currentUser = username;
    }

    private void switchToMedicinePanel() {
        frame.getContentPane().removeAll();
        frame.add(medPanel);
        createMedicinePanel();
        frame.revalidate();
        frame.repaint();
    }

    private void createMedicinePanel() {
        medPanel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.add(new JLabel("Medicine Name:"));
        medNameField = new JTextField();
        inputPanel.add(medNameField);

        inputPanel.add(new JLabel("Dosage:"));
        medDosageField = new JTextField();
        inputPanel.add(medDosageField);

        inputPanel.add(new JLabel("Frequency (minutes):"));
        medFrequencyField = new JTextField();
        inputPanel.add(medFrequencyField);

        JButton addButton = new JButton("Add Medicine");
        addButton.addActionListener(e -> addMedicine());
        inputPanel.add(addButton);

        JButton displayButton = new JButton("Display Medicines");
        displayButton.addActionListener(e -> displayMedicines());
        inputPanel.add(displayButton);

        medPanel.add(inputPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Name", "Dosage", "Frequency"}, 0);
        medicineTable = new JTable(tableModel);
        medPanel.add(new JScrollPane(medicineTable), BorderLayout.CENTER);
    }

    private void addMedicine() {
        String medName = medNameField.getText();
        String medDosage = medDosageField.getText();
        String medFrequency = medFrequencyField.getText();

        if (!medName.isEmpty() && !medDosage.isEmpty() && !medFrequency.isEmpty()) {
            Map<String, String> medicine = new HashMap<>();
            medicine.put("name", medName);
            medicine.put("dosage", medDosage);
            medicine.put("frequency", medFrequency);

            users.get(currentUser).add(medicine);
            saveToCSV();
            clearMedicineFields();
            scheduleMedicineReminder(medName, Integer.parseInt(medFrequency));
        } else {
            JOptionPane.showMessageDialog(frame, "Please enter medicine information.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void saveToCSV() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("medicine_data.csv"))) {
            for (Map.Entry<String, List<Map<String, String>>> entry : users.entrySet()) {
                for (Map<String, String> med : entry.getValue()) {
                    writer.println(entry.getKey() + "," + med.get("name") + "," + med.get("dosage") + "," + med.get("frequency"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void scheduleMedicineReminder(String medName, int frequency) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                showNotification(medName);
            }
        }, frequency * 60 * 1000, frequency * 60 * 1000); // Run at fixed rate every 'frequency' minutes
    }

    private void showNotification(String medName) {
        JOptionPane.showMessageDialog(frame, "It's time to take your " + medName, "Medicine Reminder", JOptionPane.INFORMATION_MESSAGE);
    }

    private void displayMedicines() {
        tableModel.setRowCount(0); // Clear the table
        List<Map<String, String>> userMedicines = users.getOrDefault(currentUser, new ArrayList<>());
        for (Map<String, String> med : userMedicines) {
            tableModel.addRow(new Object[]{med.get("name"), med.get("dosage"), med.get("frequency")});
        }
    }

    private void clearMedicineFields() {
        medNameField.setText("");
        medDosageField.setText("");
        medFrequencyField.setText("");
    }

    private void loadUserData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("medicine_data.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String username = parts[0];
                Map<String, String> medicine = new HashMap<>();
                medicine.put("name", parts[1]);
                medicine.put("dosage", parts[2]);
                medicine.put("frequency", parts[3]);

                users.computeIfAbsent(username, k -> new ArrayList<>()).add(medicine);
            }
        } catch (FileNotFoundException e) {
            // No user data found yet
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Medicinedosetrackerjava app = new Medicinedosetrackerjava();
            app.frame.setVisible(true);
        });
    }
}
