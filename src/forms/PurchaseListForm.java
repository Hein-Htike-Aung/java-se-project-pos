package forms;

import entities.Employee;
import entities.Purchase;
import services.PurchaseService;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PurchaseListForm {

    private JFrame frame;
    private JTable tblPurchase;
    private List<Purchase> purchaseList = new ArrayList<>();
    private PurchaseService purchaseService;
    private DefaultTableModel dtm = new DefaultTableModel();


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PurchaseListForm window = new PurchaseListForm();
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
    public PurchaseListForm() {
        initialize();
        initializeDependency();
        setTableDesign();
        loadAllPurchases();
    }

    private void initializeDependency() {
        purchaseService = new PurchaseService();
    }

    private void setTableDesign() {
        dtm.addColumn("Id");
        dtm.addColumn("Purchase Date");
        dtm.addColumn("Employee");
        dtm.addColumn("Supplier");
        dtm.addColumn("Description");
        dtm.addColumn("Total");
        this.tblPurchase.setModel(dtm);
    }

    private void loadAllPurchases() {
        this.dtm = (DefaultTableModel) this.tblPurchase.getModel();
        this.dtm.getDataVector().removeAllElements();
        this.dtm.fireTableDataChanged();

        purchaseService.findAllPurchases().forEach(p -> {
            Object[] row = new Object[6];
            row[0] = p.getId();
            row[1] = p.getPurchaseDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            row[2] = p.getEmployee().getName();
            row[3] = p.getSupplier().getName();
            row[4] = p.getDescription().isBlank() ? "-" : p.getDescription();
            row[5] = purchaseService.findAllPurchaseDetailsByPurchaseId(p.getId() + "")
                    .stream().mapToInt(pd -> pd.getPrice() * pd.getQuantity()).sum() + " MMK";

            dtm.addRow(row);
        });

        this.tblPurchase.setModel(dtm);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1000, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(21, 91, 942, 334);
        frame.getContentPane().add(scrollPane);

        tblPurchase = new JTable();
        tblPurchase.setFont(new Font("Tahoma", Font.PLAIN, 15));
        scrollPane.setViewportView(tblPurchase);
        this.tblPurchase.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!tblPurchase.getSelectionModel().isSelectionEmpty()) {

                String id = tblPurchase.getValueAt(tblPurchase.getSelectedRow(), 0).toString();

                PurchaseDetailsForm purchaseDetailsForm = new PurchaseDetailsForm(this.purchaseService.findPurchaseById(id));
                purchaseDetailsForm.frmPurchasedetails.setVisible(true);

            }
        });
    }
}
