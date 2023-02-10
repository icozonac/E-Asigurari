import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class FirstFrame extends JFrame {
    private JComboBox<String> brandsComboBox;
    private JComboBox<String> yearsComboBox;
    private JComboBox<String> modelsComboBox;
    private JTextField engineTextField;
    private JTextField pollutionIndexTextField;
    private JTextField riskIndexTextField;
    private JTextField priceTextField;
    private JTextField ageTextField;
    private JTextField capacityTextField;
    private JButton nextButton;
    private JButton addButton;
    private Car car;

    private void updateModelsComboBox() {
        modelsComboBox.removeAllItems();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("masini.txt"));
            String line = reader.readLine();
            while (line != null) {
                String[] values = line.split(" ");
                if ((values[0].equals(brandsComboBox.getSelectedItem()))
                        && (Integer.parseInt(values[1]) == Integer
                                .parseInt((String) yearsComboBox.getSelectedItem()))) {
                    modelsComboBox.addItem(values[2]);
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void updateYearsComboBox() {

        yearsComboBox.removeAllItems();

        Set<String> uniqueYears = new TreeSet<>(Collections.reverseOrder());
        try {
            BufferedReader reader = new BufferedReader(new FileReader("masini.txt"));
            String line = reader.readLine();
            while (line != null) {
                String[] values = line.split(" ");
                if (values[0].equals(brandsComboBox.getSelectedItem())) {
                    uniqueYears.add(values[1]);
                }
                line = reader.readLine();
            }
            reader.close();
            uniqueYears.forEach(yearsComboBox::addItem);
            updateModelsComboBox();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public FirstFrame() {

        setTitle("E-Asigurări mașini");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JPanel titluNouPanel = new JPanel();
        JLabel titluNouLabel = new JLabel("Asigurare nouă");
        titluNouLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titluNouPanel.add(titluNouLabel);
        titluNouPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        add(titluNouPanel, BorderLayout.NORTH);

        // Add brand combo box
        JLabel brandLabel = new JLabel("Marcă:");
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(brandLabel, constraints);

        try {
            FileReader fileReader = new FileReader("masini.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            Set<String> brands = new HashSet<>();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] lineValues = line.split(" ");
                String brand = lineValues[0].toUpperCase();
                brands.add(brand);
            }
            brandsComboBox = new JComboBox<>(brands.toArray(new String[0]));
            constraints.gridx = 2;
            mainPanel.add(brandsComboBox, constraints);
            brandsComboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateYearsComboBox();
                }

            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add year combo box
        JLabel yearLabel = new JLabel("An:");
        constraints.gridx = 3;
        mainPanel.add(yearLabel, constraints);

        yearsComboBox = new JComboBox<>();
        constraints.gridx = 4;
        mainPanel.add(yearsComboBox, constraints);

        yearsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (yearsComboBox.getSelectedItem() != null) {
                    updateModelsComboBox();
                }
            }
        });

        // Add model combo box
        JLabel modelLabel = new JLabel("Model:");
        constraints.gridx = 1;
        constraints.gridy = 2;
        mainPanel.add(modelLabel, constraints);

        modelsComboBox = new JComboBox<>();
        constraints.gridx = 2;
        mainPanel.add(modelsComboBox, constraints);

        modelsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader("masini.txt"));
                    String line = reader.readLine();
                    while (line != null) {
                        String[] values = line.split(" ");
                        if (values[0].equals(brandsComboBox.getSelectedItem())
                                && values[1].equals(yearsComboBox.getSelectedItem())
                                && values[2].equals(modelsComboBox.getSelectedItem())) {
                            // Seteaza atributul car
                            car = new Car(values[0], Integer.parseInt(values[1]), values[2],
                                    Integer.parseInt(values[3]),
                                    Double.parseDouble(values[4]), values[5],
                                    Integer.parseInt(values[6]));
                        }
                        line = reader.readLine();

                    }
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                // Seteaza valorile in text field-uri folosind metodele get din clasa Car
                engineTextField.setText(String.valueOf(car.getHorsePower()));
                pollutionIndexTextField.setText(String.valueOf(car.getindexPollution()));
                riskIndexTextField.setText(String.valueOf(car.getindexRisk()));
                priceTextField.setText(String.valueOf(car.getintialPrice()));
                ageTextField.setText(String.valueOf(car.getAge()));
                capacityTextField.setText(String.valueOf(car.getEngineSize()));

            }
        });

        // Add engine text field
        JLabel engineLabel = new JLabel("Putere motor (CP):");
        constraints.gridx = 1;
        constraints.gridy = 3;
        mainPanel.add(engineLabel, constraints);

        engineTextField = new JTextField(10);
        engineTextField.setEditable(false);
        constraints.gridx = 2;
        mainPanel.add(engineTextField, constraints);

        // Add pollution index text field
        JLabel pollutionIndexLabel = new JLabel("Indice poluare:");
        constraints.gridx = 3;
        mainPanel.add(pollutionIndexLabel, constraints);

        pollutionIndexTextField = new JTextField(10);
        pollutionIndexTextField.setEditable(false);
        constraints.gridx = 4;
        mainPanel.add(pollutionIndexTextField, constraints);

        //
        JLabel capacityLabel = new JLabel("Capacitate motor:");
        constraints.gridx = 1;
        constraints.gridy = 6;
        mainPanel.add(capacityLabel, constraints);

        capacityTextField = new JTextField(10);
        capacityTextField.setEditable(false);
        constraints.gridx = 2;
        mainPanel.add(capacityTextField, constraints);

        // Add risk index text field
        JLabel riskIndexLabel = new JLabel("Indice risc:");
        constraints.gridx = 1;
        constraints.gridy = 7;
        mainPanel.add(riskIndexLabel, constraints);

        riskIndexTextField = new JTextField(10);
        riskIndexTextField.setEditable(false);
        constraints.gridx = 2;
        mainPanel.add(riskIndexTextField, constraints);

        // Add price text field
        JLabel priceLabel = new JLabel("Prețul initial:");
        constraints.gridx = 3;
        mainPanel.add(priceLabel, constraints);

        priceTextField = new JTextField(10);
        priceTextField.setEditable(false);
        constraints.gridx = 4;
        mainPanel.add(priceTextField, constraints);

        // Add age text field
        JLabel ageLabel = new JLabel("Vârstă:");
        constraints.gridx = 3;
        constraints.gridy = 6;
        mainPanel.add(ageLabel, constraints);

        ageTextField = new JTextField(10);
        ageTextField.setEditable(false);
        constraints.gridx = 4;
        mainPanel.add(ageTextField, constraints);

        add(mainPanel);
        // add button
        addButton = new JButton("Masina noua");
        constraints.gridx = 2;
        constraints.gridy = 8;
        mainPanel.add(addButton, constraints);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CarInputFrame carInputFrame = new CarInputFrame();

            }
        });

        // add next button
        nextButton = new JButton("Mai departe");
        constraints.gridx = 3;
        constraints.gridy = 8;
        mainPanel.add(nextButton, constraints);

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (modelsComboBox.getSelectedItem() == null)
                    JOptionPane.showMessageDialog(FirstFrame.this, "Selectati un model!");
                else {
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader("masini.txt"));
                        String line = reader.readLine();
                        while (line != null) {
                            String[] values = line.split(" ");
                            if (values[0].equals(brandsComboBox.getSelectedItem())
                                    && values[1].equals(yearsComboBox.getSelectedItem())
                                    && values[2].equals(modelsComboBox.getSelectedItem())) {
                                // Seteaza atributul car
                                car = Car.CreateInstance(values[0], Integer.parseInt(values[1]), values[2],
                                        Integer.parseInt(values[3]),
                                        Double.parseDouble(values[4]), values[5],
                                        Integer.parseInt(values[6]));
                            }
                            line = reader.readLine();

                        }
                        reader.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        // Deschide fișierul clienti.txt în modul adăugare
                        BufferedWriter writer = new BufferedWriter(new FileWriter("clienti.txt", true));

                        writer.newLine();
                        writer.write(car.getBrand() + "," + car.getModel() + ",");

                        writer.close();

                        dispose();
                        ClientFrame frame2 = new ClientFrame();
                        frame2.setVisible(true);

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });

        JPanel titluPlataPanel = new JPanel();
        JLabel titluPlataLabel = new JLabel("Plata asigurării");
        titluPlataLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titluPlataPanel.add(titluPlataLabel);
        titluPlataPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        constraints.gridx = 2;
        constraints.gridy = 9;
        constraints.gridwidth = 2;
        mainPanel.add(titluPlataPanel, constraints);
        JButton plataButton = new JButton("Plata");
        constraints.gridx = 2;
        constraints.gridy = 10;
        constraints.gridwidth = 2;
        mainPanel.add(plataButton, constraints);

        plataButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                PaymentFrame paymentFrame = new PaymentFrame();
                paymentFrame.setLocationRelativeTo(null);
                paymentFrame.setVisible(true);
                paymentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });

    }

    public static void main(String[] args) {
        FirstFrame frame = new FirstFrame();
        frame.setVisible(true);

    }
}
