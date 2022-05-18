package forms;

import java.awt.EventQueue;

import javax.swing.*;
import java.awt.Font;

import entities.Category;
import entities.Employee;
import services.CategoryService;
import services.ProductService;
import shared.utils.CurrentUserHolder;

import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class CategoryForm {

    private JFrame frmCategory;
    private JTextField txtCategory;
    private JTable tblCategory;
    private JTextField txtSearch;
    private CategoryService categoryService;
    private DefaultTableModel dtm = new DefaultTableModel();
    private Category category;
    private List<Category> origianlCategoryList = new ArrayList<>();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CategoryForm window = new CategoryForm();
                    window.frmCategory.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public CategoryForm() {
        initialize();
        initializeDependency();
        this.setTableDesign();
        this.loadAllCategories(Optional.empty());
    }

    private void loadAllCategories(Optional<List<Category>> optionalCategories) {
        this.dtm = (DefaultTableModel) this.tblCategory.getModel();
        this.dtm.getDataVector().removeAllElements();
        this.dtm.fireTableDataChanged();

        this.origianlCategoryList = this.categoryService.findAllCategories();
        List<Category> categoryList = optionalCategories.orElseGet(() -> origianlCategoryList);

        categoryList.forEach(c -> {
            Object[] row = new Object[2];
            row[0] = c.getId();
            row[1] = c.getName();
            dtm.addRow(row);
        });

        this.tblCategory.setModel(dtm);
    }

    private void setTableDesign() {
        dtm.addColumn("ID");
        dtm.addColumn("Category");
        this.tblCategory.setModel(dtm);
    }

    private void initializeDependency() {
        this.categoryService = new CategoryService();
        this.categoryService.setProductRepo(new ProductService());
    }

    private void resetFormData() {
        txtCategory.setText("");
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmCategory = new JFrame();
        frmCategory.setTitle("Category");
        frmCategory.setBounds(100, 100, 1000, 500);
        frmCategory.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmCategory.getContentPane().setLayout(null);

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (null != category && category.getId() != 0) {
                    category.setName(txtCategory.getText());

                    if (!category.getName().isBlank()) {
                        categoryService.updateCategory(String.valueOf(category.getId()), category);
                        resetFormData();
                        loadAllCategories(Optional.empty());
                        category = null;
                    } else {
                        JOptionPane.showMessageDialog(null, "Enter required field");
                    }
                } else {
                    Category category = new Category();
                    category.setName(txtCategory.getText());

                    if (null != category.getName() && !category.getName().isBlank()) {

                        categoryService.saveCategory(category);
                        resetFormData();
                        loadAllCategories(Optional.empty());
                    } else {
                        JOptionPane.showMessageDialog(null, "Enter Required Field!");
                    }
                }

            }
        });
        btnSave.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnSave.setBounds(403, 65, 85, 29);
        frmCategory.getContentPane().add(btnSave);

        txtCategory = new JTextField();
        txtCategory.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtCategory.setBounds(95, 65, 290, 29);
        frmCategory.getContentPane().add(txtCategory);
        txtCategory.setColumns(10);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (null != category) {
                    categoryService.deleteCategory(category.getId() + "");
                    resetFormData();
                    loadAllCategories(Optional.empty());
                    category = null;
                } else {
                    JOptionPane.showMessageDialog(null, "Choose Category");
                }
            }
        });
        btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnDelete.setBounds(498, 65, 85, 29);
        frmCategory.getContentPane().add(btnDelete);

        JLabel lblNewLabel = new JLabel("Category");
        lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel.setBounds(0, 61, 85, 29);
        frmCategory.getContentPane().add(lblNewLabel);

        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String keyword = txtSearch.getText();

                loadAllCategories(Optional.of(origianlCategoryList.stream().filter(c -> c.getName().toLowerCase(Locale.ROOT)
                        .startsWith(keyword.toLowerCase(Locale.ROOT))).collect(Collectors.toList())));
            }
        });
        btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnSearch.setBounds(879, 65, 85, 29);
        frmCategory.getContentPane().add(btnSearch);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(24, 134, 940, 290);
        frmCategory.getContentPane().add(scrollPane);

        tblCategory = new JTable();
        tblCategory.setFont(new Font("Tahoma", Font.PLAIN, 15));
        scrollPane.setViewportView(tblCategory);
        this.tblCategory.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!tblCategory.getSelectionModel().isSelectionEmpty()) {

                String id = tblCategory.getValueAt(tblCategory.getSelectedRow(), 0).toString();

                category = categoryService.findById(id);

                txtCategory.setText(category.getName());

            }
        });

        txtSearch = new JTextField();
        txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtSearch.setColumns(10);
        txtSearch.setBounds(687, 65, 181, 29);
        frmCategory.getContentPane().add(txtSearch);
    }
}
