package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class EmployeeMainView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField cnpTextField;
	private JTextField nameTextField;
	private JTextField addressTextField;
	
	private JTable clientsTable;
	private JTable accountsTable;
	private DefaultTableModel clientsTableModel;
	private DefaultTableModel accountsTableModel;
	private JScrollPane clientsTableScroll;
	private JScrollPane accountsTableScroll;
	private JTextField balanceTextField;
	
	private JButton createClientBtn;
	private JButton updateClientBtn;
	private JButton createAccountBtn;
	private JButton updateAccountBtn;
	private JButton deleteAccountBtn;
	private JButton processBillBtn;
	private JButton transferBtn;
	
	private JComboBox<String> typeCombo;
	
	/**
	 * Create the frame.
	 */
	public EmployeeMainView() {
		setTitle("Employee");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 602, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		cnpTextField = new JTextField();
		cnpTextField.setBounds(29, 71, 132, 20);
		contentPane.add(cnpTextField);
		cnpTextField.setColumns(10);
		
		nameTextField = new JTextField();
		nameTextField.setBounds(29, 112, 132, 20);
		contentPane.add(nameTextField);
		nameTextField.setColumns(10);
		
		addressTextField = new JTextField();
		addressTextField.setBounds(29, 156, 132, 20);
		contentPane.add(addressTextField);
		addressTextField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("CNP");
		lblNewLabel.setBounds(29, 54, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(29, 99, 46, 14);
		contentPane.add(lblName);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(29, 142, 46, 14);
		contentPane.add(lblAddress);
		
		createClientBtn = new JButton("create client");
		createClientBtn.setBounds(29, 224, 132, 23);
		contentPane.add(createClientBtn);
		
		updateClientBtn = new JButton("update client");
		updateClientBtn.setBounds(30, 264, 132, 23);
		contentPane.add(updateClientBtn);
		
		Object[] clientColumns = {"Name", "Cnp", "Address" , "Id Card"};
		clientsTableModel = new DefaultTableModel(clientColumns, 0);
		
		clientsTable = new JTable(clientsTableModel);
		clientsTable.setBounds(280, 36, 236, 180);
		    
		clientsTableScroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		clientsTableScroll.setViewportView(clientsTable);
		clientsTableScroll.setBounds(183, 67, 236, 135);
		contentPane.add(clientsTableScroll);
		
		
		Object[] accountColumns = {"Id","Balance", "Date", "Type"};
		accountsTableModel = new DefaultTableModel(accountColumns, 0);
		
		accountsTable = new JTable(accountsTableModel);
		accountsTable.setBounds(280, 36, 236, 180);
		    
		accountsTableScroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		accountsTableScroll.setViewportView(accountsTable);
		accountsTableScroll.setBounds(183, 246, 236, 94);
		contentPane.add(accountsTableScroll);
		
		JLabel lblAllClients = new JLabel("All clients");
		lblAllClients.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblAllClients.setBounds(185, 41, 80, 14);
		contentPane.add(lblAllClients);
		
		JLabel lblAccountsOfSelected = new JLabel("Accounts of selected client");
		lblAccountsOfSelected.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAccountsOfSelected.setBounds(183, 221, 165, 14);
		contentPane.add(lblAccountsOfSelected);
		
		balanceTextField = new JTextField();
		balanceTextField.setColumns(10);
		balanceTextField.setBounds(432, 112, 132, 20);
		contentPane.add(balanceTextField);
		
		JLabel lblType = new JLabel("Type");
		lblType.setBounds(432, 54, 46, 14);
		contentPane.add(lblType);
		
		JLabel lblBalance = new JLabel("Balance");
		lblBalance.setBounds(432, 99, 65, 14);
		contentPane.add(lblBalance);
		
		createAccountBtn = new JButton("create account");
		createAccountBtn.setBounds(432, 179, 132, 23);
		contentPane.add(createAccountBtn);
		
		updateAccountBtn = new JButton("update account");
		updateAccountBtn.setBounds(432, 218, 132, 23);
		contentPane.add(updateAccountBtn);
		
		deleteAccountBtn = new JButton("delete account");
		deleteAccountBtn.setBounds(432, 252, 132, 23);
		contentPane.add(deleteAccountBtn);
		
		processBillBtn = new JButton("process bill");
		processBillBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		processBillBtn.setBounds(431, 286, 132, 23);
		contentPane.add(processBillBtn);
		
		transferBtn = new JButton("transfer amount to");
		transferBtn.setBounds(431, 322, 133, 23);
		contentPane.add(transferBtn);
		
		JLabel lblClient = new JLabel("Client");
		lblClient.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblClient.setBounds(29, 29, 46, 14);
		contentPane.add(lblClient);
		
		JLabel lblAccount = new JLabel("Account");
		lblAccount.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAccount.setBounds(432, 29, 75, 14);
		contentPane.add(lblAccount);
		
		typeCombo = new JComboBox<String>();
		typeCombo.addItem(new String("saving"));
		typeCombo.addItem(new String("spending"));
		typeCombo.setBounds(429, 71, 135, 20);
		contentPane.add(typeCombo);

		setVisible(true);
	}

	public void setCreateClientBtnListener(ActionListener listener) {
		createClientBtn.addActionListener(listener);  
		
	}

	public void setUpdateClientBtnListener(ActionListener listener) {
		updateClientBtn.addActionListener(listener);
		
	}

	public void setCreateAccountBtnListener(ActionListener listener) {
		createAccountBtn.addActionListener(listener);
		
	}

	public void setUpdateAccountBtnListener(ActionListener listener) {
		updateAccountBtn.addActionListener(listener);
		
	}

	public void setDeleteAccountBtnListener(ActionListener listener) {
		deleteAccountBtn.addActionListener(listener);
		
	}

	public void setProcessBillBtnListener(ActionListener listener) {
		processBillBtn.addActionListener(listener);
		
	}

	public void setTransferBtnListener(ActionListener listener) {
		transferBtn.addActionListener(listener);
		
	}

	public void setClientsTableListener(MouseListener listener) {
		clientsTable.addMouseListener(listener);
		
	}
	
	public void setAccountsTableListener(MouseListener listener) {
		accountsTable.addMouseListener(listener);
		
	}

	public String getCnpTextField() {
		return cnpTextField.getText();
	}

	public String getNameTextField() {
		return nameTextField.getText();
	}

	public String getAddressTextField() {
		return addressTextField.getText();
	}

	public DefaultTableModel getClientsTableModel() {
		return clientsTableModel;
	}
	
	public DefaultTableModel getAccountsTableModel() {
		return accountsTableModel;
	}
	
	public JTable getClientsTable() {
		return clientsTable;
	}
	
	public JTable getAccountsTable() {
		return accountsTable;
	}

	public void setCnpTextField(String cnp) {
		cnpTextField.setText(cnp);
		
	}

	public void setNameTextField(String name) {
		nameTextField.setText(name);
		
	}

	public void setAddressTextField(String address) {
		addressTextField.setText(address);
		
	}

	public String getTypeCombo() {
		return typeCombo.getSelectedItem().toString();
	}

	public String getBalanceTextField() {
		return balanceTextField.getText();
	}

	public void setBalanceTextField(String balance) {
		balanceTextField.setText(balance);
	}
	
}
