package forms;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PurchaseEditForm {

	private JFrame frmPurchase;
	private JTable tblPurchaseDetails;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PurchaseEditForm window = new PurchaseEditForm();
					window.frmPurchase.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PurchaseEditForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPurchase = new JFrame();
		frmPurchase.setTitle("Purchase");
		frmPurchase.setBounds(100, 100, 1000, 586);
		frmPurchase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPurchase.getContentPane().setLayout(null);
		
		JLabel lblSupplier = new JLabel("Supplier");
		lblSupplier.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSupplier.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSupplier.setBounds(20, 63, 85, 29);
		frmPurchase.getContentPane().add(lblSupplier);
		
		JComboBox<String> cboSupplier = new JComboBox<String>();
		cboSupplier.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cboSupplier.setBounds(109, 63, 156, 29);
		frmPurchase.getContentPane().add(cboSupplier);
		
		JLabel lblEmployee = new JLabel("Employee");
		lblEmployee.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmployee.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblEmployee.setBounds(798, 11, 143, 29);
		frmPurchase.getContentPane().add(lblEmployee);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(47, 154, 894, 204);
		frmPurchase.getContentPane().add(scrollPane);
		
		tblPurchaseDetails = new JTable();
		tblPurchaseDetails.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrollPane.setViewportView(tblPurchaseDetails);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRemove.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnRemove.setBounds(842, 106, 99, 29);
		frmPurchase.getContentPane().add(btnRemove);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDate.setBounds(722, 50, 218, 29);
		frmPurchase.getContentPane().add(lblDate);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSave.setBounds(842, 466, 99, 29);
		frmPurchase.getContentPane().add(btnSave);
		
		JTextArea txtADescription = new JTextArea();
		txtADescription.setFont(new Font("Monospaced", Font.PLAIN, 15));
		txtADescription.setBounds(51, 389, 890, 46);
		frmPurchase.getContentPane().add(txtADescription);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCancel.setBounds(733, 466, 99, 29);
		frmPurchase.getContentPane().add(btnCancel);
		
		JLabel lblProduct = new JLabel("Product");
		lblProduct.setHorizontalAlignment(SwingConstants.RIGHT);
		lblProduct.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblProduct.setBounds(275, 63, 72, 29);
		frmPurchase.getContentPane().add(lblProduct);
		
		JComboBox<String> cboProduct = new JComboBox<String>();
		cboProduct.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cboProduct.setBounds(357, 63, 156, 29);
		frmPurchase.getContentPane().add(cboProduct);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAdd.setBounds(533, 63, 99, 29);
		frmPurchase.getContentPane().add(btnAdd);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnEdit.setBounds(733, 106, 99, 29);
		frmPurchase.getContentPane().add(btnEdit);
	}
}
