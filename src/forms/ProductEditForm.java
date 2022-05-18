package forms;

import entities.Brand;
import entities.Category;
import entities.Product;
import services.BrandService;
import services.CategoryService;
import services.ProductService;
import services.PurchaseService;

import java.awt.EventQueue;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

public class ProductEditForm {

    public JFrame frame;
    private JTextField txtProductName;
    private JTextField txtQuantity;
    private JTextField txtPrice;
    private JComboBox<String> cboCategory = new JComboBox<>();
    private JComboBox<String> cboBrand = new JComboBox<>();

    private ProductService productService;
    private CategoryService categoryService;
    private BrandService brandService;
    private Product product;
    private List<Category> categoryList;
    private List<Brand> brandList;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ProductEditForm window = new ProductEditForm();
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
    public ProductEditForm() {
        initialize();
        this.initializeDependency();
        this.loadBrandsForComboBox();
        this.loadCategoriesForComboBox();
    }

    public ProductEditForm(Product product) {
    	this.product = product;
    	initialize();
        this.initializeDependency();
        this.loadBrandsForComboBox();
        this.loadCategoriesForComboBox();        
        cboBrand.setSelectedItem(product.getBrand().getName());
        cboCategory.setSelectedItem(product.getCategory().getName());
    }

    private void loadBrandsForComboBox() {
        cboBrand.addItem("- Select -");
        this.brandList = this.brandService.findAllBrands();
        this.brandList.forEach(b -> cboBrand.addItem(b.getName()));
    }

    private void loadCategoriesForComboBox() {
        cboCategory.addItem("- Select -");
        this.categoryList = this.categoryService.findAllCategories();
        this.categoryList.forEach(c -> cboCategory.addItem(c.getName()));
    }

    private void initializeDependency() {
        this.productService = new ProductService();
        this.productService.setPurchaseRepo(new PurchaseService());
        this.brandService = new BrandService();
        this.brandService.setProductRepo(this.productService);
        this.categoryService = new CategoryService();
        this.categoryService.setProductRepo(this.productService);
    }

    private void resetFormData() {
        txtProductName.setText("");
        txtQuantity.setText("");
        txtPrice.setText("");
        cboCategory.setSelectedIndex(0);
        cboBrand.setSelectedIndex(0);
    }

    private void setProductDataFromForm(Product product) {
        product.setName(txtProductName.getText());
        product.setQuantity(Integer.parseInt(txtQuantity.getText().isBlank() ? "0" : txtQuantity.getText()));
        product.setPrice(Integer.parseInt(txtPrice.getText().isBlank() ? "0" : txtPrice.getText()));
        Optional<Category> selectedCategory = categoryList.stream()
                .filter(c -> c.getName().equals(cboCategory.getSelectedItem())).findFirst();
        product.setCategory(selectedCategory.orElse(null));
        Optional<Brand> selectedBrand = brandList.stream()
                .filter(b -> b.getName().equals(cboBrand.getSelectedItem())).findFirst();
        product.setBrand(selectedBrand.orElse(null));
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 437, 561);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblProductName = new JLabel("Product Name");
        lblProductName.setHorizontalAlignment(SwingConstants.LEFT);
        lblProductName.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblProductName.setBounds(57, 68, 129, 29);
        frame.getContentPane().add(lblProductName);

        JLabel lblBrand = new JLabel("Brand");
        lblBrand.setHorizontalAlignment(SwingConstants.LEFT);
        lblBrand.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblBrand.setBounds(57, 137, 85, 29);
        frame.getContentPane().add(lblBrand);

        JLabel lblCategory = new JLabel("Category");
        lblCategory.setHorizontalAlignment(SwingConstants.LEFT);
        lblCategory.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblCategory.setBounds(57, 209, 85, 29);
        frame.getContentPane().add(lblCategory);

        JLabel lblQuantity = new JLabel("Quantity");
        lblQuantity.setHorizontalAlignment(SwingConstants.LEFT);
        lblQuantity.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblQuantity.setBounds(57, 287, 85, 29);
        frame.getContentPane().add(lblQuantity);

        JLabel lblPrice = new JLabel("Price");
        lblPrice.setHorizontalAlignment(SwingConstants.LEFT);
        lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblPrice.setBounds(57, 356, 85, 29);
        frame.getContentPane().add(lblPrice);

        txtProductName = new JTextField(product != null ? product.getName() : "");
        txtProductName.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtProductName.setColumns(10);
        txtProductName.setBounds(55, 98, 298, 29);
        frame.getContentPane().add(txtProductName);

        txtQuantity = new JTextField(product != null ? product.getQuantity() + "" : "");
        txtQuantity.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtQuantity.setColumns(10);
        txtQuantity.setBounds(57, 317, 298, 29);
        frame.getContentPane().add(txtQuantity);

        txtPrice = new JTextField(product != null ? product.getPrice() + "" : "");
        txtPrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtPrice.setColumns(10);
        txtPrice.setBounds(57, 390, 298, 29);
        frame.getContentPane().add(txtPrice);

        cboCategory.setFont(new Font("Tahoma", Font.PLAIN, 15));
        cboCategory.setBounds(57, 248, 296, 29);
        frame.getContentPane().add(cboCategory);

        cboBrand.setFont(new Font("Tahoma", Font.PLAIN, 15));
        cboBrand.setBounds(57, 176, 296, 29);
        frame.getContentPane().add(cboBrand);

        JButton btnSave = new JButton(product != null ? "Edit" : "Create");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (null != product && product.getId() != 0) {

                    setProductDataFromForm(product);

                    if (!product.getName().isBlank() &&
                            product.getPrice() >= 0 &&
                            product.getQuantity() >= 0 &&
                            null != product.getBrand() &&
                            null != product.getCategory()) {

                        productService.updateProduct(String.valueOf(product.getId()), product);
                        resetFormData();
                        ProductListForm productListForm = new ProductListForm();
                        productListForm.frmProductlist.setVisible(true);
                        frame.setVisible(false);

                    } else {
                        JOptionPane.showMessageDialog(null, "Check Required Field");
                    }
                } else {
                    Product product = new Product();
                    setProductDataFromForm(product);

                    if (!product.getName().isBlank() &&
                            product.getPrice() >= 0 &&
                            product.getQuantity() >= 0 &&
                            null != product.getBrand() &&
                            null != product.getCategory()) {

                        productService.createProduct(product);
                        resetFormData();
                        ProductListForm productListForm = new ProductListForm();
                        productListForm.frmProductlist.setVisible(true);
                        frame.setVisible(false);

                    } else {
                        JOptionPane.showMessageDialog(null, "Check Required Field");
                    }
                }

            }
        });
        btnSave.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnSave.setBounds(270, 458, 85, 29);
        frame.getContentPane().add(btnSave);


        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                ProductListForm productListForm = new ProductListForm();
                productListForm.frmProductlist.setVisible(true);
                frame.setVisible(false);

            }
        });
        btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnCancel.setBounds(161, 458, 85, 29);
        frame.getContentPane().add(btnCancel);
    }
}
