package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EmployeeMainView extends JFrame {

	private JPanel contentPane;
	private JTextField cnpTextField;
	private JTextField nameTextField;
	private JTextField addressTextField;
	private JTable clientsTable;
	private JTable accountsTable;
	private JTextField typeTextField;
	private JTextField balanceTextField;
	private JTextField creationDate;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeMainView frame = new EmployeeMainView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EmployeeMainView() {
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
		
		JButton createClientBtn = new JButton("create client");
		createClientBtn.setBounds(29, 224, 132, 23);
		contentPane.add(createClientBtn);
		
		JButton updateClientBtn = new JButton("update client");
		updateClientBtn.setBounds(30, 264, 132, 23);
		contentPane.add(updateClientBtn);
		
		clientsTable = new JTable();
		clientsTable.setBounds(208, 169, 222, -137);
		contentPane.add(clientsTable);
		
		accountsTable = new JTable();
		accountsTable.setBounds(208, 331, 244, -139);
		contentPane.add(accountsTable);
		
		JLabel lblAllClients = new JLabel("All clients");
		lblAllClients.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblAllClients.setBounds(208, 11, 80, 14);
		contentPane.add(lblAllClients);
		
		JLabel lblAccountsOfSelected = new JLabel("Accounts of selected client");
		lblAccountsOfSelected.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAccountsOfSelected.setBounds(208, 169, 165, 14);
		contentPane.add(lblAccountsOfSelected);
		
		typeTextField = new JTextField();
		typeTextField.setColumns(10);
		typeTextField.setBounds(432, 71, 132, 20);
		contentPane.add(typeTextField);
		
		balanceTextField = new JTextField();
		balanceTextField.setColumns(10);
		balanceTextField.setBounds(432, 112, 132, 20);
		contentPane.add(balanceTextField);
		
		creationDate = new JTextField();
		creationDate.setColumns(10);
		creationDate.setBounds(432, 156, 132, 20);
		contentPane.add(creationDate);
		
		JLabel lblType = new JLabel("Type");
		lblType.setBounds(432, 54, 46, 14);
		contentPane.add(lblType);
		
		JLabel lblBalance = new JLabel("Balance");
		lblBalance.setBounds(432, 99, 65, 14);
		contentPane.add(lblBalance);
		
		JLabel lblCreationDate = new JLabel("Creation Date");
		lblCreationDate.setBounds(432, 142, 132, 14);
		contentPane.add(lblCreationDate);
		
		JButton createAccountBtn = new JButton("create account");
		createAccountBtn.setBounds(432, 184, 132, 23);
		contentPane.add(createAccountBtn);
		
		JButton updateAccountBtn = new JButton("update account");
		updateAccountBtn.setBounds(432, 218, 132, 23);
		contentPane.add(updateAccountBtn);
		
		JButton deleteAccountBtn = new JButton("delete account");
		deleteAccountBtn.setBounds(432, 252, 132, 23);
		contentPane.add(deleteAccountBtn);
		
		JButton processBillBtn = new JButton("process bill");
		processBillBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		processBillBtn.setBounds(431, 286, 132, 23);
		contentPane.add(processBillBtn);
		
		JButton transferBtn = new JButton("transfer amount to");
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
	}
}
