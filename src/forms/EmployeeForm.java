package forms;

import entities.Employee;
import services.EmployeeService;

import java.awt.EventQueue;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

public class EmployeeForm {

    public JFrame frame;
    private JTextField txtName;
    private JTextField txtPhone;
    private JTextField txtEmail;
    private JTextField txtAddress;
    private JTextField txtSearch;
    private JTable tblEmployee;
    private EmployeeService employeeService;
    private DefaultTableModel dtm = new DefaultTableModel();
    private Employee employee;
    private List<Employee> originalEmployeeList = new ArrayList<>();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    EmployeeForm window = new EmployeeForm();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public EmployeeForm() {
        initialize();
        this.initializeDependency();
        this.setTableDesign();
        this.loadAllEmployees(Optional.empty());
    }

    private void initializeDependency() {
        this.employeeService = new EmployeeService();
    }

    private void setTableDesign() {
        dtm.addColumn("ID");
        dtm.addColumn("Name");
        dtm.addColumn("Phone");
        dtm.addColumn("Email");
        dtm.addColumn("Address");
        dtm.addColumn("Username");
        dtm.addColumn("Status");
        this.tblEmployee.setModel(dtm);
    }

    private void loadAllEmployees(Optional<List<Employee>> optionalEmployees) {
        this.dtm = (DefaultTableModel) this.tblEmployee.getModel();
        this.dtm.getDataVector().removeAllElements();
        this.dtm.fireTableDataChanged();

        this.originalEmployeeList = this.employeeService.findAllEmployees();
        List<Employee> employeeList = optionalEmployees.orElseGet(() -> originalEmployeeList)
                .stream().sorted((a, b) -> Boolean.compare(b.isActive(), a.isActive())).collect(Collectors.toList());

        employeeList.forEach(e -> {
            Object[] row = new Object[7];
            row[0] = e.getId();
            row[1] = e.getName();
            row[2] = e.getPhone();
            row[3] = e.getEmail();
            row[4] = e.getAddress();
            row[5] = e.getUsername();
            row[6] = e.isActive() ? "Active" : "Inactive";
            dtm.addRow(row);
        });

        this.tblEmployee.setModel(dtm);
    }

    private void resetFormData() {
        txtName.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1000, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblName = new JLabel("Name");
        lblName.setHorizontalAlignment(SwingConstants.LEFT);
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblName.setBounds(47, 39, 85, 29);
        frame.getContentPane().add(lblName);

        txtName = new JTextField();
        txtName.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtName.setColumns(10);
        txtName.setBounds(47, 78, 193, 29);
        frame.getContentPane().add(txtName);

        JLabel lblPhone = new JLabel("Phone");
        lblPhone.setHorizontalAlignment(SwingConstants.LEFT);
        lblPhone.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblPhone.setBounds(47, 117, 85, 29);
        frame.getContentPane().add(lblPhone);

        txtPhone = new JTextField();
        txtPhone.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtPhone.setColumns(10);
        txtPhone.setBounds(47, 156, 193, 29);
        frame.getContentPane().add(txtPhone);

        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtEmail.setColumns(10);
        txtEmail.setBounds(47, 234, 193, 29);
        frame.getContentPane().add(txtEmail);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setHorizontalAlignment(SwingConstants.LEFT);
        lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblEmail.setBounds(47, 195, 85, 29);
        frame.getContentPane().add(lblEmail);

        JLabel lblAddress = new JLabel("Address");
        lblAddress.setHorizontalAlignment(SwingConstants.LEFT);
        lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblAddress.setBounds(47, 273, 85, 29);
        frame.getContentPane().add(lblAddress);

        txtAddress = new JTextField();
        txtAddress.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtAddress.setColumns(10);
        txtAddress.setBounds(47, 312, 193, 29);
        frame.getContentPane().add(txtAddress);

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (null != employee && employee.getId() != 0) {

                    employee.setName(txtName.getText());
                    employee.setPhone(txtPhone.getText());
                    employee.setEmail(txtEmail.getText());
                    employee.setAddress(txtAddress.getText());

                    if (!employee.getName().isBlank() &&
                            !employee.getPhone().isBlank() &&
                            !employee.getEmail().isBlank() &&
                            !employee.getAddress().isBlank()) {

                        employeeService.updateEmployee(String.valueOf(employee.getId()), employee);
                        resetFormData();
                        loadAllEmployees(Optional.empty());
                        employee = null;

                    } else {
                        JOptionPane.showMessageDialog(null, "Enter Required Field");
                    }
                } else {
                    Employee employee = new Employee();
                    employee.setName(txtName.getText());
                    employee.setPhone(txtPhone.getText());
                    employee.setEmail(txtEmail.getText());
                    employee.setAddress(txtAddress.getText());

                    if (!employee.getName().isBlank() &&
                            !employee.getPhone().isBlank() &&
                            !employee.getEmail().isBlank() &&
                            !employee.getAddress().isBlank()) {

                        employeeService.createEmployee(employee);
                        resetFormData();
                        loadAllEmployees(Optional.empty());

                    } else {
                        JOptionPane.showMessageDialog(null, "Enter Required Field");
                    }
                }

            }
        });
        btnSave.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnSave.setBounds(47, 395, 85, 29);
        frame.getContentPane().add(btnSave);

        JButton btnDelete = new JButton("Block");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (null != employee) {
                    employeeService.blockEmployee(String.valueOf(employee.getId()));
                    resetFormData();
                    loadAllEmployees(Optional.empty());
                    employee = null;
                } else {
                    JOptionPane.showMessageDialog(null, "Choose Employee");
                }

            }
        });
        btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnDelete.setBounds(155, 395, 85, 29);
        frame.getContentPane().add(btnDelete);

        txtSearch = new JTextField();
        txtSearch.setToolTipText("");
        txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtSearch.setColumns(10);
        txtSearch.setBounds(669, 78, 165, 29);
        frame.getContentPane().add(txtSearch);

        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String keyword = txtSearch.getText();

                loadAllEmployees(Optional.of(originalEmployeeList.stream().filter(employee -> employee.getName().toLowerCase(Locale.ROOT)
                        .startsWith(keyword.toLowerCase(Locale.ROOT))).collect(Collectors.toList())));

            }
        });
        btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnSearch.setBounds(854, 78, 85, 29);
        frame.getContentPane().add(btnSearch);

        JButton btnCreateAccount = new JButton("CreateAccount");
        btnCreateAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (null != employee) {
                    frame.setVisible(false);
                    CredentialInfoForm credentialInfoForm = new CredentialInfoForm(employee);
                    credentialInfoForm.frame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Choose Employee");
                }

            }
        });
        btnCreateAccount.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnCreateAccount.setBounds(47, 351, 193, 29);
        frame.getContentPane().add(btnCreateAccount);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(276, 132, 662, 292);
        frame.getContentPane().add(scrollPane);

        tblEmployee = new JTable();
        tblEmployee.setFont(new Font("Tahoma", Font.PLAIN, 15));
        scrollPane.setViewportView(tblEmployee);
        this.tblEmployee.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!tblEmployee.getSelectionModel().isSelectionEmpty()) {

                String id = tblEmployee.getValueAt(tblEmployee.getSelectedRow(), 0).toString();

                employee = employeeService.findEmployeeById(id);

                txtName.setText(employee.getName());
                txtPhone.setText(employee.getPhone());
                txtEmail.setText(employee.getEmail());
                txtAddress.setText(employee.getAddress());

            }
        });
    }

}
