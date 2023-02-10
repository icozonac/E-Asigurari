import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.text.SimpleDateFormat;

public class ThirdFrame extends JFrame {

    private JCheckBox accidentCheckBox, earthquakeCheckBox, fireCheckBox, allCheckBox;
    private JRadioButton monthRadio, sixMonthRadio, yearRadio;
    private JButton calculateButton, printButton;

    private boolean imbunatatiri = false;
    private Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    String todayDate = formatter.format(date);

    private Car car;
    private Client client;
    private Contract contract;
    private Employee employee;
    private int contractID;
    public Calendar calendar;

    public ThirdFrame() {
        car = Car.GetInstance();
        client = Client.GetInstance();
        setTitle("E-Asigurări mașini");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel titluNouPanel = new JPanel();
        JLabel titluNouLabel = new JLabel("Încheirea contractului de asigurare");
        titluNouLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titluNouPanel.add(titluNouLabel);
        titluNouPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        add(titluNouPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Add all check box
        allCheckBox = new JCheckBox("Toate");
        constraints.gridx = 0;
        constraints.gridy = 0;
        mainPanel.add(allCheckBox, constraints);

        // Add accident check box
        accidentCheckBox = new JCheckBox("Accident");
        constraints.gridx = 1;
        mainPanel.add(accidentCheckBox, constraints);

        // Add earthquake check box
        earthquakeCheckBox = new JCheckBox("Cutremur");
        constraints.gridx = 2;
        mainPanel.add(earthquakeCheckBox, constraints);

        // Add fire check box
        fireCheckBox = new JCheckBox("Incendiu");
        constraints.gridx = 3;
        mainPanel.add(fireCheckBox, constraints);

        allCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (allCheckBox.isSelected()) {
                    allCheckBox.setSelected(false);
                    accidentCheckBox.setSelected(true);
                    earthquakeCheckBox.setSelected(true);
                    fireCheckBox.setSelected(true);

                }

            }
        });

        // Add imbunatarile aduse masinii
        JLabel imbunatiriJLabel = new JLabel("Îmbunățirele aduse: ");
        constraints.gridx = 1;
        constraints.gridy = 1;
        mainPanel.add(imbunatiriJLabel, constraints);

        JTextField imbunatiriTextField = new JTextField(15);

        constraints.gridx = 2;
        constraints.gridy = 1;
        mainPanel.add(imbunatiriTextField, constraints);

        // Add payment method
        JLabel paymentMethodJLabel = new JLabel("Metoda de plată: ");
        constraints.gridx = 0;
        constraints.gridy = 2;
        mainPanel.add(paymentMethodJLabel, constraints);

        ButtonGroup paymentMethodButtonGroup = new ButtonGroup();

        monthRadio = new JRadioButton("Lunar");
        constraints.gridx = 1;
        mainPanel.add(monthRadio, constraints);

        sixMonthRadio = new JRadioButton("Semestrial");
        constraints.gridx = 2;
        mainPanel.add(sixMonthRadio, constraints);

        yearRadio = new JRadioButton("Anual");
        constraints.gridx = 3;
        mainPanel.add(yearRadio, constraints);

        paymentMethodButtonGroup.add(monthRadio);
        paymentMethodButtonGroup.add(sixMonthRadio);
        paymentMethodButtonGroup.add(yearRadio);

        // Add rata
        JLabel rataLabel = new JLabel("Rata finală: ");
        constraints.gridx = 1;
        constraints.gridy = 4;
        mainPanel.add(rataLabel, constraints);

        JTextField rataTextField = new JTextField(10);
        rataTextField.setEditable(false);
        constraints.gridx = 1;
        constraints.gridy = 5;
        mainPanel.add(rataTextField, constraints);

        // Add rata finala
        JLabel rataReducereLabel = new JLabel("Rata cu reducere: ");
        constraints.gridx = 2;
        constraints.gridy = 4;
        mainPanel.add(rataReducereLabel, constraints);

        JTextField rataReducereTextField = new JTextField(10);
        rataReducereTextField.setEditable(false);
        rataReducereTextField.setFont(rataReducereTextField.getFont().deriveFont(Font.BOLD));
        constraints.gridx = 2;
        constraints.gridy = 5;
        mainPanel.add(rataReducereTextField, constraints);

        // Add calculate button
        calculateButton = new JButton("Calculează");
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(calculateButton, constraints);

        // add event listener to the calculate button
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if ((accidentCheckBox.isSelected() ||
                        earthquakeCheckBox.isSelected() ||
                        fireCheckBox.isSelected())
                        && (monthRadio.isSelected() || sixMonthRadio.isSelected() || yearRadio.isSelected())) {
                    double rata = 0;
                    if (accidentCheckBox.isSelected())
                        rata += 400;
                    if (earthquakeCheckBox.isSelected())
                        rata += 200;
                    if (fireCheckBox.isSelected())
                        rata += 200;

                    if (imbunatiriTextField.getText().length() > 0) {
                        imbunatatiri = true;
                    } else {
                        imbunatatiri = false;
                    }

                    rataTextField.setText(String.valueOf(String.format("%.1f", calculateRate(rata))));

                    if (yearRadio.isSelected()) {
                        rataReducereTextField.setText(String.valueOf(String.format("%.1f", calculateRate(rata) * 0.9)));

                    }
                    if (sixMonthRadio.isSelected()) {
                        rataReducereTextField
                                .setText(String.valueOf(String.format("%.1f", calculateRate(rata) * 0.95)));

                    }
                    if (monthRadio.isSelected()) {
                        rataReducereTextField.setText(String.valueOf(String.format("%.1f", calculateRate(rata))));
                    }

                } else {
                    JOptionPane.showMessageDialog(ThirdFrame.this, "Nu ati selectat toate opriunile necesare!");
                }
            }
        });

        // Add print button
        printButton = new JButton("Tipărește");
        constraints.gridx = 2;
        constraints.gridy = 3;
        mainPanel.add(printButton, constraints);
        // add event listener to the print button
        printButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String employeeName = JOptionPane.showInputDialog("Numele angajatului:");
                String employeePosition = JOptionPane.showInputDialog("Pozitia angajatului:");

                employee = new Employee(employeeName, employeePosition);

                File file = new File("ListaContracte.txt");
                try {
                    Scanner sc = new Scanner(file);
                    sc.nextLine(); // salt prima linie
                    if (!sc.hasNextLine()) {
                        contractID = 1;
                    } else {
                        while (sc.hasNextLine()) {
                            String line = sc.nextLine();
                            String[] parts = line.split(",");
                            contractID = Integer.parseInt(parts[0]);

                        }
                        contractID++;
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("ListaContracte.txt", true));
                    writer.newLine();

                    if (monthRadio.isSelected()) {
                        Date startDate = new Date();
                        calendar = Calendar.getInstance();
                        calendar.setTime(startDate);
                        calendar.add(Calendar.MONTH, 1);
                        Date endDate = calendar.getTime();
                        String rataString = rataReducereTextField.getText();
                        rataString = rataString.replace(',', '.');
                        double rata = Double.parseDouble(rataString);
                        String startDateF = formatter.format(startDate);
                        String endDateF = formatter.format(endDate);
                        writer.write(
                                contractID + "," + "Lunar" + "," + rata + "," + rata
                                        + "," + startDateF + "," + endDateF + "," + car.getBrand() + ","
                                        + car.getModel()
                                        + "," + client.getName() + "," + client.getfirstName() + "," + client.getCNP()
                                        + "," + employee.getName() + "," + employee.getPosition());
                        contract = new Contract("Lunar", rata);
                    } else if (sixMonthRadio.isSelected()) {
                        Date startDate = new Date();
                        calendar = Calendar.getInstance();
                        calendar.setTime(startDate);
                        calendar.add(Calendar.MONTH, 6);
                        Date endDate = calendar.getTime();
                        String rataString = rataReducereTextField.getText();
                        rataString = rataString.replace(',', '.');
                        double rata = Double.parseDouble(rataString);
                        String startDateF = formatter.format(startDate);
                        String endDateF = formatter.format(endDate);
                        writer.write(
                                contractID + "," + "Semestrial" + ","
                                        + rata + "," + rata
                                        + "," + startDateF + "," + endDateF + "," + car.getBrand() + ","
                                        + car.getModel()
                                        + "," + client.getName() + "," + client.getfirstName() + "," + client.getCNP()
                                        + "," + employee.getName() + "," + employee.getPosition());
                        contract = new Contract("Semestrial", rata);

                    } else {
                        Date startDate = new Date();
                        calendar = Calendar.getInstance();
                        calendar.setTime(startDate);
                        calendar.add(Calendar.YEAR, 1);
                        Date endDate = calendar.getTime();
                        String rataString = rataReducereTextField.getText();
                        rataString = rataString.replace(',', '.');
                        double rata = Double.parseDouble(rataString);
                        String startDateF = formatter.format(startDate);
                        String endDateF = formatter.format(endDate);
                        writer.write(
                                contractID + "," + "Anual" + "," + rata + "," + rata
                                        + "," + startDateF + "," + endDateF + "," + car.getBrand() + ","
                                        + car.getModel()
                                        + "," + client.getName() + "," + client.getfirstName() + "," + client.getCNP()
                                        + "," + employee.getName() + "," + employee.getPosition());
                        contract = new Contract("Anual", rata);
                    }

                    writer.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    File file1 = new File("contract\\contract.txt");

                    // If file doesn't exists, then create it
                    if (!file1.exists()) {
                        file1.createNewFile();
                    }

                    FileWriter fileWriter = new FileWriter(file1);

                    // Writes the content to the file
                    fileWriter.write("E-Asigurari Auto\n");
                    fileWriter.write("\n");
                    fileWriter.write("                       Asigurare A Autovehiculelor\n");
                    fileWriter.write("\n");
                    fileWriter.write("Informatii despre client:\n");
                    fileWriter.write("1. Numele clientului: " + client.getfirstName() + " " + client.getName() + "\n");
                    fileWriter.write("2. Adresa clientului: " + client.getAddress() + "\n");
                    fileWriter.write("3. CNP clientului: " + client.getCNP() + "\n");
                    fileWriter.write("4. Numarul de telefon: " + client.getPhone() + "\n");
                    fileWriter.write("\n");
                    fileWriter.write("Informatii despre autovehiculul asigurat:\n");
                    fileWriter.write("\n");
                    fileWriter.write("Marca: " + car.getBrand() + "\n");
                    fileWriter.write("Model: " + car.getModel() + "\n");
                    fileWriter.write("Anul fabricatiei: " + car.getYear() + "\n");
                    fileWriter.write("\n");
                    fileWriter.write("Informatii despre asigurare:\n");
                    fileWriter.write("Nr. asigurare: " + contractID + "\n");
                    fileWriter.write("Rata: " + contract.getRate() + "\n");
                    fileWriter.write("Tipul asigurarii: " + contract.getType() + "\n");
                    fileWriter.write("\n");
                    fileWriter.write("Declar ca cele incluse in prezenta asigurare sunt adevarate si complete, in\n");
                    fileWriter.write("conformitate cu prevederile legale in vigoare si in confirmitate cu\n");
                    fileWriter.write("informatiile de care dispun, fiind de acord ca aceasta sa stea la baza si sa\n");
                    fileWriter.write("fie parte integranta a contractului de asigurare.\n");
                    fileWriter.write("\n");
                    fileWriter.write("Data completarii: " + todayDate + "\n");
                    fileWriter.write("\n");
                    fileWriter.write(String.format("Contractant,%60s\n", employee.getPosition()));
                    fileWriter.write(String.format("%s%60s\n", client.getName(), employee.getName()));
                    fileWriter.write("Semnatura:                                                       Semnatura:\n");
                    fileWriter.flush();
                    fileWriter.close();

                    JOptionPane.showMessageDialog(ThirdFrame.this, "Contractul a fost salvat și tipărit cu succes!");
                    dispose();
                    FirstFrame firstFrame = new FirstFrame();
                    firstFrame.setVisible(true);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }

        });

        add(mainPanel, BorderLayout.CENTER);

    }

    public double calculateRate(double rata) {

        double rataFinala = rata;

        if (car.getintialPrice() < 200000)
            rataFinala += 500;
        if (car.getAge() > 5)
            rataFinala += 200;
        if (imbunatatiri)
            rataFinala += 100;
        if (car.getindexRisk() > 20)
            rataFinala *= 1.1;
        if (client.getAccident())
            rataFinala *= 1.2;

        return rataFinala;

    }

}
