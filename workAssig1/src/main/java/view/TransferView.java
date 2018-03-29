package view;

import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class TransferView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField destAccountTextField;
	private JTextField amountTextField;
	private JTextField sourceAccountTextField;
	private JButton okBtn;
	private JButton cancelBtn;
	private JTextField dateTextField;
	private JLabel lblDate;
	private JTextField clientTextField;
	private JLabel lblClientId;

	public TransferView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblPleaseTypeThe = new JLabel("Id of the destination account");
		lblPleaseTypeThe.setBounds(44, 121, 152, 14);
		contentPane.add(lblPleaseTypeThe);

		destAccountTextField = new JTextField();
		destAccountTextField.setBounds(199, 118, 193, 20);
		contentPane.add(destAccountTextField);
		destAccountTextField.setColumns(10);

		JLabel lblAmount = new JLabel("Amount ");
		lblAmount.setBounds(44, 158, 54, 14);
		contentPane.add(lblAmount);

		amountTextField = new JTextField();
		amountTextField.setColumns(10);
		amountTextField.setBounds(199, 155, 193, 20);
		contentPane.add(amountTextField);

		okBtn = new JButton("Ok");
		okBtn.setBounds(67, 206, 89, 23);
		contentPane.add(okBtn);

		cancelBtn = new JButton("Cancel");
		cancelBtn.setBounds(263, 206, 89, 23);
		contentPane.add(cancelBtn);

		sourceAccountTextField = new JTextField();
		sourceAccountTextField.setColumns(10);
		sourceAccountTextField.setBounds(199, 87, 193, 20);
		contentPane.add(sourceAccountTextField);
		sourceAccountTextField.setEditable(false);

		JLabel lblIdOfThe = new JLabel("Id of the source account");
		lblIdOfThe.setBounds(44, 90, 152, 14);
		contentPane.add(lblIdOfThe);
		
		dateTextField = new JTextField();
		dateTextField.setBounds(91, 11, 86, 20);
		contentPane.add(dateTextField);
		dateTextField.setColumns(10);
		dateTextField.setEditable(false);
		
		lblDate = new JLabel("Date");
		lblDate.setBounds(43, 14, 46, 14);
		contentPane.add(lblDate);
		
		clientTextField = new JTextField();
		clientTextField.setEditable(false);
		clientTextField.setColumns(10);
		clientTextField.setBounds(93, 39, 86, 20);
		clientTextField.setEditable(false);
		contentPane.add(clientTextField);
		
		lblClientId = new JLabel("Client id");
		lblClientId.setBounds(37, 42, 46, 14);
		contentPane.add(lblClientId);
		
		setVisible(false);
	}

	public void setSourceTextField(String sourceId) {
		sourceAccountTextField.setText(sourceId);
	}

	public void setOkBtnListener(ActionListener listener) {
		okBtn.addActionListener(listener);
	}

	public void setCancelBtnListener(ActionListener listener) {
		cancelBtn.addActionListener(listener);
	}

	public String getSourceTextField() {
		return sourceAccountTextField.getText();
	}

	public String getDestTextField() {
		return destAccountTextField.getText();
	}

	public String getAmountTextField() {
		return amountTextField.getText();
	}
	
	public String getDateTextField() {
		return dateTextField.getText();
	}
	
	public void setDateTextField(String dateString) {
		dateTextField.setText(dateString);
	}
	
	public String getClientTextField() {
		return clientTextField.getText();
	}
	
	public void setClientTextField(String client) {
		clientTextField.setText(client);
	}

	public void setDestTextField(String string) {
		destAccountTextField.setText(string);
	}
	
	public void setAmountTextField(String string) {
		amountTextField.setText(string);
	}
}
