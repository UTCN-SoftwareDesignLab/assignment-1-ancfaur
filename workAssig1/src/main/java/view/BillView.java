package view;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class BillView extends JFrame {

	private JPanel showBill;
	private JButton processBtn, cancelBtn, searchBtn;
	private JTextArea textArea;
	private JTextField billIdTextField;
	private JTextField dateTextField;
	private JLabel lblDate;
	private JTextField accountTextField;
	private JLabel lblAccoutId;
	private JTextField clientTextField;
	private JLabel lblClientId;

	public BillView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 410, 293);
		showBill = new JPanel();
		showBill.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(showBill);
		showBill.setLayout(null);
		
		billIdTextField = new JTextField();
		billIdTextField.setBounds(31, 121, 123, 20);
		showBill.add(billIdTextField);
		billIdTextField.setColumns(10);
		
		searchBtn = new JButton("Search for bill");
		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		searchBtn.setBounds(31, 152, 123, 23);
		showBill.add(searchBtn);
		
		processBtn = new JButton("Process");
		processBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		processBtn.setBounds(31, 186, 123, 23);
		showBill.add(processBtn);
		
		cancelBtn = new JButton("cancel");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		cancelBtn.setBounds(31, 220, 123, 23);
		showBill.add(cancelBtn);
		
		JLabel lblNewLabel = new JLabel("Bill Id");
		lblNewLabel.setBounds(31, 96, 46, 14);
		showBill.add(lblNewLabel);
		
		textArea = new JTextArea();
		textArea.setBounds(177, 31, 196, 198);
		showBill.add(textArea);
		
		dateTextField = new JTextField();
		dateTextField.setColumns(10);
		dateTextField.setBounds(87, 11, 65, 20);
		showBill.add(dateTextField);
		
		lblDate = new JLabel("Date");
		lblDate.setBounds(31, 11, 46, 14);
		showBill.add(lblDate);
		
		accountTextField = new JTextField();
		accountTextField.setColumns(10);
		accountTextField.setBounds(87, 36, 65, 20);
		accountTextField.setEditable(false);
		showBill.add(accountTextField);
		
		
		lblAccoutId = new JLabel("Accout id");
		lblAccoutId.setBounds(31, 36, 46, 14);
		showBill.add(lblAccoutId);
		
		clientTextField = new JTextField();
		clientTextField.setColumns(10);
		clientTextField.setBounds(87, 65, 65, 20);
		clientTextField.setEditable(false);
		showBill.add(clientTextField);
		
		lblClientId = new JLabel("Client id");
		lblClientId.setBounds(31, 61, 46, 14);
		showBill.add(lblClientId);
		setVisible(false);
	}

	public String getBillIdTextField() {
		return billIdTextField.getText();
	}
	

	public void setTextArea(String text) {
		textArea.setText(text);
		
	}

	public void setSearchBtnListener(ActionListener l) {
		searchBtn.addActionListener(l);
	}
	
	public void setCancelBtnListener(ActionListener l) {
		cancelBtn.addActionListener(l);
	}
	
	public void setProcessBtnListener(ActionListener l) {
		processBtn.addActionListener(l);
	}
	
	public void setDateTextField(String dateString) {
		dateTextField.setText(dateString);
	}
	
	public String getDateTextField() {
		return dateTextField.getText();
	}


	public void setClientTextField(String string) {
		clientTextField.setText(string);	
	}

	public void setAccountTextField(String string) {
		accountTextField.setText(string);
	}

	public void setBillTextField(String string) {
		billIdTextField.setText("");
	}
	
	
}
