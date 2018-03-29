package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;

public class LoginView extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameTextField;
	private JTextField passwordTextField;
	private JButton btnLogin;

	public LoginView() {
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		JLabel lblWelcome = new JLabel("Welcome");
		lblWelcome.setFont(new Font("Mongolian Baiti", Font.PLAIN, 27));
		lblWelcome.setBounds(173, 50, 108, 23);
		contentPane.add(lblWelcome);

		usernameTextField = new JTextField();
		usernameTextField.setHorizontalAlignment(SwingConstants.LEFT);
		usernameTextField.setBounds(133, 100, 191, 20);
		contentPane.add(usernameTextField);
		usernameTextField.setColumns(10);

		passwordTextField = new JTextField();
		passwordTextField.setHorizontalAlignment(SwingConstants.LEFT);
		passwordTextField.setBounds(133, 143, 191, 20);
		contentPane.add(passwordTextField);
		passwordTextField.setColumns(10);

		JLabel lblInsertUsername = new JLabel("Username");
		lblInsertUsername.setBounds(133, 88, 86, 14);
		contentPane.add(lblInsertUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(133, 131, 86, 14);
		contentPane.add(lblPassword);

		btnLogin = new JButton("Login");
		btnLogin.setBounds(195, 184, 86, 23);
		contentPane.add(btnLogin);
		
		setVisible(false);
	}

	public String getUsername() {
		return usernameTextField.getText();
	}

	public String getPassword() {
		return passwordTextField.getText();
	}

	public void setLoginButtonListener(ActionListener loginButtonListener) {
		btnLogin.addActionListener(loginButtonListener);
	}
}
