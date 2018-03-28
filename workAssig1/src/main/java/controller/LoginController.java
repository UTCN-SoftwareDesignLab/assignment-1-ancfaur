package controller;

import model.Role;
import model.User;
import model.validation.Notification;
import repository.user.AuthenticationException;
import service.user.AuthenticationService;
import view.AdministatorMainView;
import view.EmployeeMainView;
import view.LoginView;

import javax.swing.*;

import factory.ComponentFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoginController {
	private final LoginView loginView;
	private final AuthenticationService authenticationService;

	public LoginController(LoginView loginView, AuthenticationService authenticationService) {
		this.loginView = loginView;
		this.authenticationService = authenticationService;
		loginView.setLoginButtonListener(new LoginButtonListener());
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
					ComponentFactory componentFactory = ComponentFactory.instance(new Boolean(false));
					User user = loginNotification.getResult();
					if (user.getRoles().get(0).getRole().equals("administrator"))
						new AdministratorMainController(new AdministatorMainView(),
								componentFactory.getUserRepository(), componentFactory.getAuthenticationService());
					else
						new EmployeeMainController(new EmployeeMainView(), componentFactory.getClientService(),
								componentFactory.getAccountService(), componentFactory.getBillRepository());

				}
			}
		}
	}

}