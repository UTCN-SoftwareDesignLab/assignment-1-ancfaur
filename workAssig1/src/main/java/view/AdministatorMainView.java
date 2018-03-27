package view;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Font;

public class AdministatorMainView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel tableModel;
	private JScrollPane tableScroll;
	private JButton createEmployeeBtn;
	private JButton updateEmployeeBtn;
	private JButton deleteEmployeeBtn;
	private JButton generateReportBtn;
	private JTextArea textArea;
	private JTextField usernameTextField;
	private JTextField passwordTextField;
	private JLabel lblEmployeeDetails;
	
	
	
	/**
	 * Create the application.
	 */
	public AdministatorMainView() {
		initialize();
		setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("Administrator");
		setBounds(100, 100, 571, 399);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		setContentPane(contentPane);
		
		contentPane.setLayout(null);
		
		createEmployeeBtn = new JButton("create employee");
		createEmployeeBtn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		createEmployeeBtn.setBounds(29, 36, 218, 23);
		contentPane.add(createEmployeeBtn);
		
		updateEmployeeBtn = new JButton("update employee");
		updateEmployeeBtn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		updateEmployeeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		updateEmployeeBtn.setBounds(29, 86, 218, 23);
		contentPane.add(updateEmployeeBtn);
		
		deleteEmployeeBtn = new JButton("delete employee");
		deleteEmployeeBtn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		deleteEmployeeBtn.setBounds(29, 141, 218, 23);
		contentPane.add(deleteEmployeeBtn);
		
		generateReportBtn = new JButton("generate activity report");
		generateReportBtn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		generateReportBtn.setBounds(29, 193, 218, 23);
		contentPane.add(generateReportBtn);
		
		Object[] columns = {"Username" , "Role"};
		tableModel = new DefaultTableModel(columns, 0);
		
		table = new JTable(tableModel);
		table.setBounds(280, 36, 236, 180);
		    
		tableScroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		tableScroll.setViewportView(table);
		tableScroll.setBounds(280, 36, 236, 180);
		contentPane.add(tableScroll);
	
		
	
		    
		textArea = new JTextArea();
		textArea.setBounds(29, 237, 218, 112);
		contentPane.add(textArea);
		
		usernameTextField = new JTextField();
		usernameTextField.setBounds(280, 266, 236, 20);
		contentPane.add(usernameTextField);
		usernameTextField.setColumns(10);
		
		passwordTextField = new JTextField();
		passwordTextField.setBounds(280, 309, 236, 20);
		contentPane.add(passwordTextField);
		passwordTextField.setColumns(10);
		
		JLabel lblUsername = new JLabel("username");
		lblUsername.setBounds(280, 252, 91, 14);
		contentPane.add(lblUsername);
		
		JLabel lblRole = new JLabel("password");
		lblRole.setBounds(279, 294, 72, 14);
		contentPane.add(lblRole);
		
		lblEmployeeDetails = new JLabel("Employee Details");
		lblEmployeeDetails.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEmployeeDetails.setBounds(279, 227, 114, 14);
		contentPane.add(lblEmployeeDetails);
		
	}

	public void setCreateBtnListener(ActionListener createButtonListener) {
		createEmployeeBtn.addActionListener(createButtonListener);
		
	}
	
	public void setUpdateBtnListener(ActionListener updateButtonListener) {
		updateEmployeeBtn.addActionListener(updateButtonListener);	
	}
	
	public void setDeleteBtnListener(ActionListener deleteButtonListener) {
		deleteEmployeeBtn.addActionListener(deleteButtonListener);	
	}
	
	public void setReportBtnListener(ActionListener reportButtonListener) {
		generateReportBtn.addActionListener(reportButtonListener);	
	}
	
	
	public DefaultTableModel getTableModel() {
		return tableModel;
	}
	
	public JTable getTable() {
		return table;
	}
	
	public void setUsernameTextField(String text) {
		usernameTextField.setText(text);
	}
	
	public String getUsernameTextField() {
		return usernameTextField.getText();
	}
	
	public void setPasswordTextField(String text) {
		passwordTextField.setText(text);
	}
	
	public String getPasswordTextField() {
		return passwordTextField.getText();
	}
	
	public void setJtableListener(MouseListener listener) {
		table.addMouseListener(listener);
		
	}
	
	public void setTextAreaText(String text) {
		textArea.setText(text);
	}
	
}
