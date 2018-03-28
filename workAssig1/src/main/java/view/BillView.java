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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BillView frame = new BillView();
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
	public BillView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 410, 200);
		showBill = new JPanel();
		showBill.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(showBill);
		showBill.setLayout(null);
		
		billIdTextField = new JTextField();
		billIdTextField.setBounds(31, 22, 123, 20);
		showBill.add(billIdTextField);
		billIdTextField.setColumns(10);
		
		searchBtn = new JButton("Search for bill");
		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		searchBtn.setBounds(31, 50, 123, 23);
		showBill.add(searchBtn);
		
		processBtn = new JButton("Process");
		processBtn.setBounds(31, 84, 123, 23);
		showBill.add(processBtn);
		
		cancelBtn = new JButton("cancel");
		cancelBtn.setBounds(31, 115, 123, 23);
		showBill.add(cancelBtn);
		
		JLabel lblNewLabel = new JLabel("Bill Id");
		lblNewLabel.setBounds(43, 11, 46, 14);
		showBill.add(lblNewLabel);
		
		textArea = new JTextArea();
		textArea.setBounds(164, 20, 196, 106);
		showBill.add(textArea);
		setVisible(true);
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
	
	
}
