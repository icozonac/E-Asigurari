import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.time.temporal.ChronoUnit;

public class PaymentFrame extends JFrame {
    private JLabel idLabel;
    private JTextField idField;
    private JLabel restRateLabel, rateTotalLabel;
    private JTextField restRateField, rateTotalField;
    private JButton searchButton;
    private JTextField amountField;
    private JButton payButton;
    private JLabel typeLabel, amountLabel;
    private JTextField typeField;

    public PaymentFrame() {
        setTitle("Achitare online E-asigurare");
        setSize(600, 500);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 10, 5, 0);

        idLabel = new JLabel("ID");
        idField = new JTextField(8);
        idField.setHorizontalAlignment(JTextField.CENTER);
        rateTotalLabel = new JLabel("Rata totala:");
        rateTotalField = new JTextField(10);
        rateTotalField.setEditable(false);
        rateTotalField.setHorizontalAlignment(JTextField.CENTER);
        typeLabel = new JLabel("Tip asigurare:");
        typeField = new JTextField(10);
        typeField.setEditable(false);
        typeField.setHorizontalAlignment(JTextField.CENTER);
        restRateLabel = new JLabel("Rata ramasă:");
        restRateField = new JTextField(10);
        restRateField.setFont(new Font("Arial", Font.BOLD, 12));
        restRateField.setEditable(false);
        restRateField.setHorizontalAlignment(JTextField.CENTER);
        typeField = new JTextField(10);
        typeField.setEditable(false);
        typeField.setHorizontalAlignment(JTextField.CENTER);
        searchButton = new JButton("Caută");
        amountLabel = new JLabel("Suma");
        amountField = new JTextField(8);
        amountField.setEditable(false);
        amountField.setHorizontalAlignment(JTextField.CENTER);
        payButton = new JButton("Achită");

        constraints.gridx = 0;
        constraints.gridy = 0;
        mainPanel.add(idLabel, constraints);
        constraints.gridx = 1;
        mainPanel.add(idField, constraints);
        constraints.gridx = 2;
        mainPanel.add(searchButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        mainPanel.add(rateTotalLabel, constraints);
        constraints.gridx = 2;
        mainPanel.add(rateTotalField, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        mainPanel.add(typeLabel, constraints);
        constraints.gridx = 2;
        mainPanel.add(typeField, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        mainPanel.add(restRateLabel, constraints);
        constraints.gridx = 2;
        mainPanel.add(restRateField, constraints);

        constraints.gridy = 4;
        constraints.gridx = 0;
        mainPanel.add(amountLabel, constraints);
        constraints.gridx = 1;
        mainPanel.add(amountField, constraints);
        constraints.gridx = 2;
        mainPanel.add(payButton, constraints);
        add(mainPanel, BorderLayout.CENTER);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String rate = getDetails(id);
                rateTotalField.setText(rate);

                double requiredRate;
                if (typeField.getText().equals("Anual")) {
                    requiredRate = Double.parseDouble(rateTotalField.getText());
                } else if (typeField.getText().equals("Semestrial")) {
                    requiredRate = Double.parseDouble(rateTotalField.getText()) / 2;
                } else {
                    requiredRate = Double.parseDouble(rateTotalField.getText()) / 12;
                }
                amountField.setText(String.valueOf(requiredRate));
            }
        });

        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Double remaining = Double.parseDouble(restRateField.getText())
                        - Double.parseDouble(amountField.getText());

                restRateField.setText(String.valueOf(remaining));
                updateRate(idField.getText(), String.valueOf(remaining));
                JOptionPane.showMessageDialog(PaymentFrame.this, "Plata a fost efectuată cu succes!");

                if (remaining <= 0) {
                    JOptionPane.showMessageDialog(PaymentFrame.this, "Contractul a fost finalizat!");
                    deleteContract(idField.getText());
                    idField.setText("");
                    rateTotalField.setText("");
                    typeField.setText("");
                    restRateField.setText("");
                    amountField.setText("");

                } else if (typeField.getText().equals("Semestrial")) {
                    updateDate(idField.getText(), 6);

                } else if (typeField.getText().equals("Lunar")) {
                    updateDate(idField.getText(), 1);

                }

            }
        });
        JPanel buttonPanel = new JPanel();
        JLabel label = new JLabel("Stergerea clientilor ce nu si-au achitat rata in decurs de 15 zile");
        JButton unpaidButton = new JButton("Afisare/Stergere");
        unpaidButton.setBackground(Color.RED);
        buttonPanel.setSize(100, 100);
        buttonPanel.add(label);
        buttonPanel.add(unpaidButton);
        add(buttonPanel, BorderLayout.SOUTH);
        unpaidButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader("ListaContracte.txt"));
                    String line;
                    ArrayList<String> contracts = new ArrayList<>();
                    br.readLine();
                    while ((line = br.readLine()) != null) {
                        String[] contract = line.split(",");
                        String[] date = contract[5].split("/");
                        int day = Integer.parseInt(date[0]);
                        int month = Integer.parseInt(date[1]);
                        int year = Integer.parseInt(date[2]);
                        LocalDate contractDate = LocalDate.of(year, month, day);
                        LocalDate currentDate = LocalDate.now();
                        long days = ChronoUnit.DAYS.between(contractDate, currentDate);
                        if (days < 15 && days > 0) {
                            contracts.add("Clientul:" + contract[8] + " " + contract[9] + " cu CNP " + contract[10]
                                    + " nu a achitat rata la timp!");

                        } else if (days > 15) {
                            deleteContract(contract[0]);
                        }
                    }

                    StringBuilder sb = new StringBuilder();
                    for (var i = 0; i < contracts.size(); i++) {
                        sb.append(contracts.get(i) + "\n");
                    }
                    JOptionPane.showMessageDialog(PaymentFrame.this, sb.toString());
                    br.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private String getDetails(String id) {
        String rate = "";
        typeField.setText("");
        restRateField.setText("");
        try (BufferedReader br = new BufferedReader(new FileReader("ListaContracte.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] contract = line.split(",");
                if (contract[0].equals(id)) {
                    rate = contract[2];
                    typeField.setText(contract[1]);
                    restRateField.setText(contract[3]);
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return rate;
    }

    private void updateRate(String id, String newRate) {
        String line;
        String updatedLine = "";
        String allLines = "";
        try (BufferedReader br = new BufferedReader(new FileReader("ListaContracte.txt"))) {
            while ((line = br.readLine()) != null) {
                String[] contract = line.split(",");
                if (contract[0].equals(id)) {
                    contract[3] = newRate;
                    updatedLine = String.join(",", contract);
                } else {
                    updatedLine = line;
                }
                allLines += updatedLine + "\n";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter fw = new FileWriter("ListaContracte.txt")) {
            fw.write(allLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateDate(String id, int newDate) {
        String line;
        String updatedLine = "";
        String allLines = "";
        try (BufferedReader br = new BufferedReader(new FileReader("ListaContracte.txt"))) {
            while ((line = br.readLine()) != null) {
                String[] contract = line.split(",");
                if (contract[0].equals(id)) {
                    String dateText = contract[5];
                    LocalDate date = LocalDate.parse(dateText, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    LocalDate newDated = date.plusMonths(newDate);
                    contract[5] = newDated.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    updatedLine = String.join(",", contract);
                } else {
                    updatedLine = line;
                }
                allLines += updatedLine + "\n";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter fw = new FileWriter("ListaContracte.txt")) {
            fw.write(allLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Această implementare citi linii din fișierul txt, înregistrează toate linii
    // în afară de linia care trebuie ștearsă într-o listă, apoi rescrie toate linii
    // în fișierul txt fără linia ștearsă.

    private void deleteContract(String id) {
        List<String> lines = new ArrayList<>();
        String lineToRemove = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("ListaContracte.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] contractInfo = line.split(",");
                if (contractInfo[0].equals(id)) {
                    lineToRemove = line;
                } else {
                    lines.add(line);
                }
            }
            reader.close();

            if (!lineToRemove.equals("")) {
                lines.remove(lineToRemove);
                BufferedWriter writer = new BufferedWriter(new FileWriter("ListaContracte.txt"));
                for (String contractLine : lines) {
                    writer.write(contractLine + System.lineSeparator());
                }
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
