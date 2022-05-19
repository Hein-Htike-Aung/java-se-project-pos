package forms;

import entities.Employee;
import services.AuthService;
import services.EmployeeService;
import shared.utils.CurrentUserHolder;

import java.awt.EventQueue;

import javax.swing.*;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CredentialInfoForm {

    public JFrame frame;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private Employee employee;
    private EmployeeService employeeService;
    private AuthService authService;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CredentialInfoForm window = new CredentialInfoForm();
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
    public CredentialInfoForm() {
        initialize();
        initializeDependency();
    }

    private void initializeDependency() {
        this.employeeService = new EmployeeService();
        this.authService = new AuthService();
    }

    public CredentialInfoForm(Employee employee) {
        this.employee = employee;
        initialize();
        initializeDependency();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 553, 408);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel(employee != null ? "Employee : " + employee.getName() : "Login");
        lblNewLabel.setForeground(Color.DARK_GRAY);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Lithos Pro Regular", Font.PLAIN, 25));
        lblNewLabel.setBounds(173, 10, 200, 78);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setHorizontalAlignment(SwingConstants.LEFT);
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblUsername.setBounds(101, 76, 85, 29);
        frame.getContentPane().add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setHorizontalAlignment(SwingConstants.LEFT);
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblPassword.setBounds(101, 152, 85, 29);
        frame.getContentPane().add(lblPassword);

        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtUsername.setColumns(10);
        txtUsername.setBounds(98, 113, 346, 29);
        frame.getContentPane().add(txtUsername);

        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtPassword.setBounds(98, 194, 346, 29);
        frame.getContentPane().add(txtPassword);

        JButton btnLogin = new JButton(employee != null ? "Create Account" : "Login");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (null != employee) {
                    employee.setUsername(txtUsername.getText());
                    employee.setPassword(String.valueOf(txtPassword.getPassword()));

                    if (!employee.getUsername().isBlank() && !employee.getPassword().isBlank()) {
                        employeeService.updateEmployee(String.valueOf(employee.getId()), employee);
                        frame.setVisible(false);
                        EmployeeForm employeeForm = new EmployeeForm();
                        employeeForm.frame.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Fill required fields");
                    }
                } else {
                    String username = txtUsername.getText();
                    String password = String.valueOf(txtPassword.getPassword());

                    if (!username.isBlank() && !password.isBlank()) {
                        String loggedInUserId = authService.login(username, password);
                        if(!loggedInUserId.isBlank()) {
                            CurrentUserHolder.setLoggedInUser(employeeService.findEmployeeById(loggedInUserId));
                            JOptionPane.showMessageDialog(null, "Successfully Login");
                            frame.setVisible(false);
                            PurchaseEditForm purchaseEditForm = new PurchaseEditForm();
                            purchaseEditForm.frmPurchase.setVisible(true);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Enter required Fields");
                    }
                }

            }
        });
        btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnLogin.setBounds(147, 272, 240, 40);
        frame.getContentPane().add(btnLogin);

        JLabel lblMessage = new JLabel();
        lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
        lblMessage.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblMessage.setForeground(Color.RED);
        lblMessage.setBounds(98, 233, 346, 29);
        frame.getContentPane().add(lblMessage);
    }
}
