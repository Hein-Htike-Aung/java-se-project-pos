package forms;

import entities.Employee;
import entities.Supplier;
import services.PurchaseService;
import services.SupplierService;

import java.awt.EventQueue;

import javax.swing.*;
import java.awt.Font;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class SupplierForm {

    private JFrame fromSupplier;
    private JTextField txtPhone;
    private JTextField txtEmail;
    private JTextField txtAddress;
    private JTextField txtName;
    private JTable tblSupplier;
    private JTextField txtSearch;

    private DefaultTableModel dtm = new DefaultTableModel();
    private SupplierService supplierService;
    private List<Supplier> originalSupplierList = new ArrayList<>();
    private Supplier supplier;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SupplierForm window = new SupplierForm();
                    window.fromSupplier.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public SupplierForm() {
        initialize();
        initializeDependency();
        this.setTableDesign();
        this.loadAllSuppliers(Optional.empty());
    }

    private void setTableDesign() {
        dtm.addColumn("ID");
        dtm.addColumn("Name");
        dtm.addColumn("Phone");
        dtm.addColumn("Email");
        dtm.addColumn("Address");
        this.tblSupplier.setModel(dtm);
    }

    private void loadAllSuppliers(Optional<List<Supplier>> optionalSuppliers) {
        this.dtm = (DefaultTableModel) this.tblSupplier.getModel();
        this.dtm.getDataVector().removeAllElements();
        this.dtm.fireTableDataChanged();

        this.originalSupplierList = this.supplierService.findAllSuppliers();
        List<Supplier> supplierList = optionalSuppliers.orElseGet(() -> originalSupplierList);

        supplierList.forEach(s -> {
            Object[] row = new Object[5];
            row[0] = s.getId();
            row[1] = s.getName();
            row[2] = s.getPhone();
            row[3] = s.getEmail();
            row[4] = s.getAddress();
            dtm.addRow(row);
        });

        this.tblSupplier.setModel(dtm);
    }

    private void resetFormData() {
        txtName.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
    }

    private void initializeDependency() {
        this.supplierService = new SupplierService();
        this.supplierService.setPurchaseRepo(new PurchaseService());
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        fromSupplier = new JFrame();
        fromSupplier.setTitle("Supplier");
        fromSupplier.setBounds(100, 100, 1000, 500);
        fromSupplier.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fromSupplier.getContentPane().setLayout(null);

        JLabel lblName = new JLabel("Name");
        lblName.setHorizontalAlignment(SwingConstants.LEFT);
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblName.setBounds(71, 32, 85, 29);
        fromSupplier.getContentPane().add(lblName);

        JLabel lblPhone = new JLabel("Phone");
        lblPhone.setHorizontalAlignment(SwingConstants.LEFT);
        lblPhone.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblPhone.setBounds(71, 110, 85, 29);
        fromSupplier.getContentPane().add(lblPhone);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setHorizontalAlignment(SwingConstants.LEFT);
        lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblEmail.setBounds(71, 188, 85, 29);
        fromSupplier.getContentPane().add(lblEmail);

        JLabel lblAddress = new JLabel("Address");
        lblAddress.setHorizontalAlignment(SwingConstants.LEFT);
        lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblAddress.setBounds(71, 266, 85, 29);
        fromSupplier.getContentPane().add(lblAddress);

        txtPhone = new JTextField();
        txtPhone.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtPhone.setColumns(10);
        txtPhone.setBounds(71, 149, 165, 29);
        fromSupplier.getContentPane().add(txtPhone);

        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtEmail.setColumns(10);
        txtEmail.setBounds(71, 227, 165, 29);
        fromSupplier.getContentPane().add(txtEmail);

        txtAddress = new JTextField();
        txtAddress.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtAddress.setColumns(10);
        txtAddress.setBounds(71, 305, 165, 29);
        fromSupplier.getContentPane().add(txtAddress);

        txtName = new JTextField();
        txtName.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtName.setColumns(10);
        txtName.setBounds(71, 71, 165, 29);
        fromSupplier.getContentPane().add(txtName);

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (null != supplier && supplier.getId() != 0) {

                    supplier.setName(txtName.getText());
                    supplier.setPhone(txtPhone.getText());
                    supplier.setEmail(txtEmail.getText());
                    supplier.setAddress(txtAddress.getText());

                    if (!supplier.getName().isBlank() &&
                            !supplier.getPhone().isBlank() &&
                            !supplier.getEmail().isBlank() &&
                            !supplier.getAddress().isBlank()) {

                        supplierService.updateSupplier(String.valueOf(supplier.getId()), supplier);
                        resetFormData();
                        loadAllSuppliers(Optional.empty());
                        supplier = null;

                    } else {
                        JOptionPane.showMessageDialog(null, "Enter Required Field");
                    }
                } else {
                    Supplier supplier = new Supplier();
                    supplier.setName(txtName.getText());
                    supplier.setPhone(txtPhone.getText());
                    supplier.setEmail(txtEmail.getText());
                    supplier.setAddress(txtAddress.getText());

                    if (!supplier.getName().isBlank() &&
                            !supplier.getPhone().isBlank() &&
                            !supplier.getEmail().isBlank() &&
                            !supplier.getAddress().isBlank()) {

                        supplierService.createSupplier(supplier);
                        resetFormData();
                        loadAllSuppliers(Optional.empty());

                    } else {
                        JOptionPane.showMessageDialog(null, "Enter Required Field");
                    }
                }

            }
        });
        btnSave.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnSave.setBounds(71, 388, 85, 29);
        fromSupplier.getContentPane().add(btnSave);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (null != supplier) {
                    supplierService.deleteSupplier(String.valueOf(supplier.getId()));
                    resetFormData();
                    loadAllSuppliers(Optional.empty());
                    supplier = null;
                } else {
                    JOptionPane.showMessageDialog(null, "Choose Supplier");
                }
            }
        });
        btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnDelete.setBounds(179, 388, 85, 29);
        fromSupplier.getContentPane().add(btnDelete);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(300, 125, 664, 292);
        fromSupplier.getContentPane().add(scrollPane);

        tblSupplier = new JTable();
        tblSupplier.setFont(new Font("Tahoma", Font.PLAIN, 15));
        scrollPane.setViewportView(tblSupplier);
        this.tblSupplier.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!tblSupplier.getSelectionModel().isSelectionEmpty()) {

                String id = tblSupplier.getValueAt(tblSupplier.getSelectedRow(), 0).toString();

                supplier = supplierService.findById(id);

                txtName.setText(supplier.getName());
                txtPhone.setText(supplier.getPhone());
                txtEmail.setText(supplier.getEmail());
                txtAddress.setText(supplier.getAddress());

            }
        });

        txtSearch = new JTextField();
        txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtSearch.setColumns(10);
        txtSearch.setBounds(693, 71, 165, 29);
        fromSupplier.getContentPane().add(txtSearch);

        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String keyword = txtSearch.getText();

                loadAllSuppliers(Optional.of(originalSupplierList.stream().filter(s -> s.getName().toLowerCase(Locale.ROOT)
                        .startsWith(keyword.toLowerCase(Locale.ROOT))).collect(Collectors.toList())));
            }
        });
        btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnSearch.setBounds(878, 71, 85, 29);
        fromSupplier.getContentPane().add(btnSearch);
    }

}
