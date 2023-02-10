import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ClientFrame extends JFrame {
    private JLabel numeLabel, prenumeLabel, adresaLabel, emailLabel, telefonLabel, accidenteLabel, cnpLabel;
    private JTextField numeField, prenumeField, adresaField, emailField, telefonField, cnpField;
    private JCheckBox acciCheckBox;
    private JButton submitButton;
    private Client client;
    private int clientID;

    public ClientFrame() {

        setTitle("Date despre client");

        // cream un panel pentru titlu
        JPanel titluPanel = new JPanel();
        JLabel titluLabel = new JLabel("E-Asigurari");
        titluLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titluPanel.add(titluLabel);
        titluPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        add(titluPanel, BorderLayout.NORTH);

        // cream componentele de interfata grafica
        numeLabel = new JLabel("Nume: ");
        prenumeLabel = new JLabel("Prenume: ");
        cnpLabel = new JLabel("CNP: ");
        adresaLabel = new JLabel("Adresa: ");
        emailLabel = new JLabel("Email: ");
        telefonLabel = new JLabel("Numar de telefon: ");
        accidenteLabel = new JLabel("Accidente in ultimii 5 ani: ");
        numeField = new JTextField(20);
        prenumeField = new JTextField(20);
        cnpField = new JTextField(20);
        adresaField = new JTextField(20);
        emailField = new JTextField(20);
        telefonField = new JTextField(20);
        acciCheckBox = new JCheckBox();
        submitButton = new JButton("Adauga client");

        // cream un panel pentru a adauga componentele
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // adaugam componentele la panel
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_END;
        panel.add(numeLabel, c);

        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_START;
        panel.add(numeField, c);

        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.LINE_END;
        panel.add(prenumeLabel, c);

        c.gridx = 1;
        c.gridy = 1;
        c.anchor = GridBagConstraints.LINE_START;
        panel.add(prenumeField, c);

        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LINE_END;
        panel.add(cnpLabel, c);

        c.gridx = 1;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LINE_START;
        panel.add(cnpField, c);

        c.gridx = 0;
        c.gridy = 3;
        c.anchor = GridBagConstraints.LINE_END;
        panel.add(adresaLabel, c);

        c.gridx = 1;
        c.gridy = 3;
        c.anchor = GridBagConstraints.LINE_START;
        panel.add(adresaField, c);

        c.gridx = 0;
        c.gridy = 4;
        c.anchor = GridBagConstraints.LINE_END;
        panel.add(emailLabel, c);

        c.gridx = 1;
        c.gridy = 4;
        c.anchor = GridBagConstraints.LINE_START;
        panel.add(emailField, c);

        c.gridx = 0;
        c.gridy = 5;
        c.anchor = GridBagConstraints.LINE_END;
        panel.add(telefonLabel, c);

        c.gridx = 1;
        c.gridy = 5;
        c.anchor = GridBagConstraints.LINE_START;
        panel.add(telefonField, c);

        c.gridx = 0;
        c.gridy = 6;
        c.anchor = GridBagConstraints.LINE_END;
        panel.add(accidenteLabel, c);

        c.gridx = 1;
        c.gridy = 6;
        c.anchor = GridBagConstraints.LINE_START;
        panel.add(acciCheckBox, c);

        c.gridx = 1;
        c.gridy = 7;
        c.anchor = GridBagConstraints.LINE_END;
        panel.add(submitButton, c);

        // adaugam panel-ul la fereastra
        add(panel);

        setSize(500, 400);

        // setam fereastra sa fie centrat
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // adaugam un listener pentru butonul de submit
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // obtinem datele din componentele de interfata grafica
                String nume = numeField.getText();
                String prenume = prenumeField.getText();
                String cnp = cnpField.getText();
                String adresa = adresaField.getText();
                String email = emailField.getText();
                String telefon = telefonField.getText();
                boolean accidente = acciCheckBox.isSelected();

                // Validare a câmpurile de introducere pentru a mă asigura că nu sunt goale
                if (nume.isEmpty() || prenume.isEmpty() || cnp.isEmpty() || adresa.isEmpty() || email.isEmpty()
                        || telefon.isEmpty()) {
                    JOptionPane.showMessageDialog(ClientFrame.this, "Toate campurile sunt obligatorii!");
                    return;
                }
                if (!cnp.matches("[0-9]+")) {
                    JOptionPane.showMessageDialog(ClientFrame.this, "CNP-ul trebuie sa contina doar cifre!");
                    return;
                }
                if (cnp.length() != 13) {
                    JOptionPane.showMessageDialog(ClientFrame.this, "CNP-ul trebuie sa contina 13 cifre!");
                    return;
                }
                // Verifică dacă oricare dintre câmpurile de text conțin spații
                if (email.contains(" ")) {
                    // Afișează o eroare
                    JOptionPane.showMessageDialog(ClientFrame.this,
                            "Nu pot fi introduse spații în câmpul email!");
                    return;
                }

                if (!telefon.matches("[+-]?[0-9]+")) {
                    JOptionPane.showMessageDialog(ClientFrame.this,
                            "Numarul de telefon trebuie sa contina doar cifre!");
                    return;
                }
                // Crează un obiect de tip Client
                client = Client.CreateInstance(nume, prenume, cnp, adresa, email, telefon, accidente);

                File file = new File("clienti.txt");
                try {
                    Scanner sc = new Scanner(file);
                    sc.nextLine(); // salt prima linie
                    if (!sc.hasNextLine()) {
                        clientID = 1;
                    } else {
                        while (sc.hasNextLine()) {
                            String line = sc.nextLine();
                            String[] parts = line.split(",");
                            if (parts.length >= 3) {
                                clientID = Integer.parseInt(parts[2]);
                            }
                        }
                        clientID++;
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                try {
                    // Deschide fișierul clienti.txt în modul adăugare
                    BufferedWriter writer = new BufferedWriter(new FileWriter("clienti.txt", true));

                    writer.write(clientID + "," + client.getName() + "," + client.getfirstName() + "," + client.getCNP()
                            + ","
                            + client.getAddress() + ","
                            + client.getEmail() + "," + client.getPhone() + ","
                            + client.getAccident());

                    writer.close();

                    dispose();
                    ThirdFrame frame2 = new ThirdFrame();
                    frame2.setVisible(true);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }
}