package forms;

import entities.Employee;
import entities.Product;
import services.ProductService;
import services.PurchaseService;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.swing.table.DefaultTableModel;

public class ProductListForm {

    public JFrame frmProductlist;
    private JTable tblProducts;
    private JTextField txtSearch;
    private ProductService productService;
    private DefaultTableModel dtm = new DefaultTableModel();
    private List<Product> originalProductList = new ArrayList<>();
    private Product product;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ProductListForm window = new ProductListForm();
                    window.frmProductlist.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public ProductListForm() {
        initialize();
        initializeDependency();
        this.setTableDesign();
        this.loadAllProducts(Optional.empty());
    }

    private void initializeDependency() {
        this.productService = new ProductService();
        this.productService.setPurchaseRepo(new PurchaseService());
    }

    private void setTableDesign() {
        dtm.addColumn("ID");
        dtm.addColumn("Product");
        dtm.addColumn("Brand");
        dtm.addColumn("Category");
        dtm.addColumn("Quantity");
        dtm.addColumn("Price");
        this.tblProducts.setModel(dtm);
    }

    private void loadAllProducts(Optional<List<Product>> optionalProducts) {
        this.dtm = (DefaultTableModel) this.tblProducts.getModel();
        this.dtm.getDataVector().removeAllElements();
        this.dtm.fireTableDataChanged();

        this.originalProductList = this.productService.findAllProducts();
        List<Product> productList = optionalProducts.orElseGet(() -> originalProductList);

        productList.forEach(p -> {
            Object[] row = new Object[6];
            row[0] = p.getId();
            row[1] = p.getName();
            row[2] = p.getBrand().getName();
            row[3] = p.getCategory().getName();
            row[4] = p.getQuantity();
            row[5] = p.getPrice();
            dtm.addRow(row);
        });

        this.tblProducts.setModel(dtm);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmProductlist = new JFrame();
        frmProductlist.setTitle("ProductList");
        frmProductlist.setBounds(100, 100, 1000, 500);
        frmProductlist.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmProductlist.getContentPane().setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(36, 90, 915, 342);
        frmProductlist.getContentPane().add(scrollPane);

        tblProducts = new JTable();
        tblProducts.setFont(new Font("Tahoma", Font.PLAIN, 15));
        scrollPane.setViewportView(tblProducts);
        this.tblProducts.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!tblProducts.getSelectionModel().isSelectionEmpty()) {

                String id = tblProducts.getValueAt(tblProducts.getSelectedRow(), 0).toString();

                product = productService.findById(id);

            }
        });

        txtSearch = new JTextField();
        txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtSearch.setColumns(10);
        txtSearch.setBounds(38, 33, 193, 29);
        frmProductlist.getContentPane().add(txtSearch);

        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String keyword = txtSearch.getText();

                loadAllProducts(Optional.of(originalProductList.stream().filter(
                                p -> p.getName().toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT)) ||
                                        p.getCategory().getName().toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT)) ||
                                        p.getBrand().getName().toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT)))
                        .collect(Collectors.toList())));
            }
        });
        btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnSearch.setBounds(254, 33, 85, 29);
        frmProductlist.getContentPane().add(btnSearch);

        JButton btnAddNew = new JButton("Add New");
        btnAddNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                ProductEditForm productEditForm = new ProductEditForm();
                productEditForm.frame.setVisible(true);
                frmProductlist.setVisible(false);
            }
        });
        btnAddNew.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnAddNew.setBounds(830, 33, 126, 29);
        frmProductlist.getContentPane().add(btnAddNew);
        
        JButton btnEdit = new JButton("Edit");
        btnEdit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(null != product) {
        			ProductEditForm productEditForm = new ProductEditForm(product);
        			productEditForm.frame.setVisible(true);
        			frmProductlist.setVisible(false);
        		}else {
        			JOptionPane.showMessageDialog(null, "Choose Product");
        		}
        	}
        });
        btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnEdit.setBounds(694, 33, 126, 29);
        frmProductlist.getContentPane().add(btnEdit);

    }
}
