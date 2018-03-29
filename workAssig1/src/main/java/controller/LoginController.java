package controller;

import model.User;
import model.validation.Notification;
import repository.user.AuthenticationException;
import service.user.AuthenticationService;
import view.LoginView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginController {
	private AdministratorMainController administratorController;
	private EmployeeMainController employeeController;
	
	
	private final LoginView loginView;
	private final AuthenticationService authenticationService;

	public LoginController(LoginView loginView, AuthenticationService authenticationService, AdministratorMainController administratorController,
			EmployeeMainController employeeController) {
		
		
		this.loginView = loginView;
		this.authenticationService = authenticationService;
		loginView.setLoginButtonListener(new LoginButtonListener());
	
		this.administratorController = administratorController;
		this.employeeController = employeeController;
	}

	private class LoginButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String username = loginView.getUsername();
			String password = loginView.getPassword();

			Notification<User> loginNotification = null;
			try {
				loginNotification = authenticationService.login(username, password);
			} catch (AuthenticationException e1) {
				e1.printStackTrace();
			}

			if (loginNotification != null) {
				if (loginNotification.hasErrors()) {
					JOptionPane.showMessageDialog(loginView.getContentPane(), loginNotification.getFormattedErrors());
				} else {
					User user = loginNotification.getResult();
					if (user.getRoles().get(0).getRole().equals("administrator")) {
						administratorController.setVisible(true);
					}
					else {
						employeeController.setUser(user);
						employeeController.seVisible(true);
					}
						

				}
			}
		}
	}
	
	public void setVisible(Boolean val) {
		loginView.setVisible(val);
	}

}