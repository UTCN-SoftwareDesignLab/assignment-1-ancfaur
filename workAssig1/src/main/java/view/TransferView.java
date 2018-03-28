package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class TransferView extends JFrame {

	private JPanel contentPane;
	private JTextField destAccountTextField;
	private JTextField amountTextField;
	private JTextField sourceAccountTextField;
	private JButton okBtn;
	private JButton cancelBtn;

	public TransferView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblPleaseTypeThe = new JLabel("Id of the destination account");
		lblPleaseTypeThe.setBounds(43, 94, 152, 14);
		contentPane.add(lblPleaseTypeThe);

		destAccountTextField = new JTextField();
		destAccountTextField.setBounds(198, 91, 193, 20);
		contentPane.add(destAccountTextField);
		destAccountTextField.setColumns(10);

		JLabel lblAmount = new JLabel("Amount ");
		lblAmount.setBounds(43, 131, 54, 14);
		contentPane.add(lblAmount);

		amountTextField = new JTextField();
		amountTextField.setColumns(10);
		amountTextField.setBounds(198, 128, 193, 20);
		contentPane.add(amountTextField);

		okBtn = new JButton("Ok");
		okBtn.setBounds(66, 179, 89, 23);
		contentPane.add(okBtn);

		cancelBtn = new JButton("Cancel");
		cancelBtn.setBounds(262, 179, 89, 23);
		contentPane.add(cancelBtn);

		sourceAccountTextField = new JTextField();
		sourceAccountTextField.setColumns(10);
		sourceAccountTextField.setBounds(198, 60, 193, 20);
		contentPane.add(sourceAccountTextField);

		JLabel lblIdOfThe = new JLabel("Id of the source account");
		lblIdOfThe.setBounds(43, 63, 152, 14);
		contentPane.add(lblIdOfThe);
		
		setVisible(true);
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
}
