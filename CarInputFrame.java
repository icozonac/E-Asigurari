import java.awt.*;
import javax.swing.*;
import java.io.*;

public class CarInputFrame extends JFrame {

    private JTextField brandField, yearField, modelField, powerField, engineSizeField, priceField;
    private JComboBox<String> fuelTypeField;
    private JButton addButton;
    private JPanel mainPanel;

    public CarInputFrame() {
        JFrame frame = new JFrame("Adauga masina");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);

        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        // Create text fields for input
        brandField = new JTextField(10);
        yearField = new JTextField(10);
        modelField = new JTextField(10);
        powerField = new JTextField(10);
        engineSizeField = new JTextField(10);
        fuelTypeField = new JComboBox<String>(new String[] { "B", "D" });
        priceField = new JTextField(10);

        // Add text fields to panel
        constraints.gridx = 1;
        constraints.gridy = 0;
        mainPanel.add(new JLabel("Brand"), constraints);
        constraints.gridx = 2;
        mainPanel.add(brandField, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        mainPanel.add(new JLabel("An"), constraints);
        constraints.gridx = 2;
        mainPanel.add(yearField, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        mainPanel.add(new JLabel("Model"), constraints);
        constraints.gridx = 2;
        mainPanel.add(modelField, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        mainPanel.add(new JLabel("Cai putere"), constraints);
        constraints.gridx = 2;
        mainPanel.add(powerField, constraints);

        constraints.gridx = 1;
        constraints.gridy = 4;
        mainPanel.add(new JLabel("Marime motor"), constraints);
        constraints.gridx = 2;
        mainPanel.add(engineSizeField, constraints);
        constraints.gridx = 1;
        constraints.gridy = 5;
        mainPanel.add(new JLabel("Tip combustibil"), constraints);
        constraints.gridx = 2;
        mainPanel.add(fuelTypeField, constraints);

        constraints.gridx = 1;
        constraints.gridy = 6;
        mainPanel.add(new JLabel("Pret"), constraints);
        constraints.gridx = 2;
        mainPanel.add(priceField, constraints);

        // Create and add "Add" button
        addButton = new JButton("Adauga masina");
        addButton.addActionListener(e -> {
            if (brandField.getText().isEmpty() || yearField.getText().isEmpty() || modelField.getText().isEmpty()
                    || powerField.getText().isEmpty() || engineSizeField.getText().isEmpty()
                    || priceField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Toate campurile sunt obligatorii!");
            } else {
                String brand = brandField.getText().toUpperCase();
                String year = yearField.getText();
                String model = modelField.getText();
                String power = powerField.getText();
                String engineSize = engineSizeField.getText();
                String fuelType = (String) fuelTypeField.getSelectedItem();
                String price = priceField.getText();

                // Create a string with the car information
                String carInfo = brand + " " + year + " " + model + " " + power + " " + engineSize + " " + fuelType
                        + " "
                        + price;

                try {
                    FileWriter writer = new FileWriter("masini.txt", true);
                    writer.write(System.lineSeparator() + carInfo);
                    writer.close();
                    JOptionPane.showMessageDialog(null, "Masina a fost adaugata!");

                    frame.dispose();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Eroare la scrierea in fisier");
                }
            }
        });
        constraints.gridx = 2;
        constraints.gridy = 7;
        mainPanel.add(addButton, constraints);

        // Add panel to frame and display

        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

}