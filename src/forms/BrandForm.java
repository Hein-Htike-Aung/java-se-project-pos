package forms;

import entities.Brand;
import services.BrandService;
import services.ProductService;
import services.PurchaseService;
import services.SaleService;
import shared.mapper.ProductMapper;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BrandForm {

    public JFrame frmBrand;
    private JTextField txtBrand;
    private JTextField txtSearch;
    private JTable tblBrand;
    private BrandService brandService;
    private DefaultTableModel dtm = new DefaultTableModel();
    private Brand brand;
    private List<Brand> originalBrandList = new ArrayList<>();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BrandForm window = new BrandForm();
                    window.frmBrand.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public BrandForm() {
        initialize();
        this.initializeDependency();
        this.setTableDesign();
        this.loadAllCategories(Optional.empty());
    }

    private void loadAllCategories(Optional<List<Brand>> optionalBrands) {
        this.dtm = (DefaultTableModel) this.tblBrand.getModel();
        this.dtm.getDataVector().removeAllElements();
        this.dtm.fireTableDataChanged();

        this.originalBrandList = this.brandService.findAllBrands();
        List<Brand> brands = optionalBrands.orElseGet(() -> originalBrandList);

        brands.forEach(b -> {
            Object[] row = new Object[2];
            row[0] = b.getId();
            row[1] = b.getName();

            dtm.addRow(row);
        });

        tblBrand.setModel(dtm);
    }

    private void setTableDesign() {
        dtm.addColumn("ID");
        dtm.addColumn("Brand");
        tblBrand.setModel(dtm);
    }

    private void initializeDependency() {
        this.brandService = new BrandService();
        this.brandService.setProductRepo(new ProductService());
    }

    private void resetFormData() {
        txtBrand.setText("");
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmBrand = new JFrame();
        frmBrand.setTitle("Brand");
        frmBrand.setBounds(100, 100, 1000, 500);
        frmBrand.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmBrand.getContentPane().setLayout(null);

        JLabel lblBrand = new JLabel("Brand");
        lblBrand.setHorizontalAlignment(SwingConstants.RIGHT);
        lblBrand.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblBrand.setBounds(10, 50, 85, 29);
        frmBrand.getContentPane().add(lblBrand);

        txtBrand = new JTextField();
        txtBrand.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtBrand.setColumns(10);
        txtBrand.setBounds(105, 54, 290, 29);
        frmBrand.getContentPane().add(txtBrand);

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (null != brand && brand.getId() != 0) {

                    brand.setName(txtBrand.getText());
                    if (!brand.getName().isBlank()) {

                        brandService.updateBrand(String.valueOf(brand.getId()), brand);
                        resetFormData();
                        loadAllCategories(Optional.empty());
                        brand = null;
                    } else {
                        JOptionPane.showMessageDialog(null, "Enter Required Field!");
                    }
                } else {
                    Brand brand = new Brand();
                    brand.setName(txtBrand.getText());

                    if (null != brand.getName() && !brand.getName().isBlank()) {

                        brandService.saveBrand(brand);
                        resetFormData();
                        loadAllCategories(Optional.empty());
                    } else {
                        JOptionPane.showMessageDialog(null, "Enter Required Field!");
                    }
                }
            }
        });
        btnSave.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnSave.setBounds(419, 50, 85, 29);
        frmBrand.getContentPane().add(btnSave);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                if (null != brand) {
                    brandService.delete(brand.getId() + "");
                    resetFormData();
                    loadAllCategories(Optional.empty());
                    brand = null;
                } else {
                    JOptionPane.showMessageDialog(null, "Choose Brand");
                }
            }
        });
        btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnDelete.setBounds(514, 50, 85, 29);
        frmBrand.getContentPane().add(btnDelete);

        txtSearch = new JTextField();
        txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtSearch.setColumns(10);
        txtSearch.setBounds(700, 50, 181, 29);
        frmBrand.getContentPane().add(txtSearch);

        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String keyword = txtSearch.getText();

                loadAllCategories(Optional.of(originalBrandList.stream().filter(b -> b.getName().toLowerCase(Locale.ROOT)
                        .startsWith(keyword.toLowerCase(Locale.ROOT))).collect(Collectors.toList())));
            }
        });
        btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnSearch.setBounds(888, 50, 85, 29);
        frmBrand.getContentPane().add(btnSearch);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(27, 153, 934, 257);
        frmBrand.getContentPane().add(scrollPane);

        tblBrand = new JTable();
        tblBrand.setFont(new Font("Tahoma", Font.PLAIN, 15));
        scrollPane.setViewportView(tblBrand);
        this.tblBrand.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!tblBrand.getSelectionModel().isSelectionEmpty()) {

                String id = tblBrand.getValueAt(tblBrand.getSelectedRow(), 0).toString();

                brand = brandService.findById(id);

                txtBrand.setText(brand.getName());

            }
        });
    }
}
