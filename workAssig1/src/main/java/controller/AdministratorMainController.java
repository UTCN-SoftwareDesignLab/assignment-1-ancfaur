package controller;

import static database.Constants.Roles.EMPLOYEE;
import model.User;
import model.validation.Notification;
import repository.user.AuthenticationException;
import repository.user.UserRepository;
import service.user.AuthenticationService;
import view.AdministatorMainView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JOptionPane;

public class AdministratorMainController {
	private final AdministatorMainView administratorView;
	private final UserRepository userRepository;
	private final AuthenticationService authenticationService;
	private List<User> employees;
	private User selectedUser;

	public AdministratorMainController(AdministatorMainView administratorView, UserRepository userRepository,
			AuthenticationService authenticationService) {
		this.administratorView = administratorView;
		this.userRepository = userRepository;
		this.authenticationService = authenticationService;
		this.employees = userRepository.findAllWithRole(EMPLOYEE);

		fillJtableWithData();

		administratorView.setCreateBtnListener(new CreateButtonListener());
		administratorView.setUpdateBtnListener(new UpdateButtonListener());
		administratorView.setDeleteBtnListener(new DeleteButtonListener());
		administratorView.setReportBtnListener(new ReportButtonListener());
		administratorView.setJtableListener(new TableListener());

	}

	private void fillJtableWithData() {
		administratorView.getTableModel().setRowCount(0);
		for (User user : employees)
			administratorView.getTableModel().addRow(new Object[] { user.getUsername(), user.showListRoles() });

	}

	private void updateEmployeesList() {
		this.employees = userRepository.findAllWithRole("employee");
		fillJtableWithData();
	}

	private class CreateButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String username = administratorView.getUsernameTextField();
			String password = administratorView.getPasswordTextField();

			Notification<Boolean> registerNotification = authenticationService.register(username, password, EMPLOYEE);
			if (registerNotification.hasErrors()) {
				JOptionPane.showMessageDialog(administratorView.getContentPane(),
						registerNotification.getFormattedErrors());
			} else {
				if (!registerNotification.getResult()) {
					JOptionPane.showMessageDialog(administratorView.getContentPane(),
							"Registration not successful, please try again later.");
				} else {
					JOptionPane.showMessageDialog(administratorView.getContentPane(), "New employee registered");
					updateEmployeesList();
				}
			}
		}
	}

	private class UpdateButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (selectedUser == null) {
				administratorView.setTextAreaText("no employee selected");
			} else {
				Long userId = selectedUser.getId();
				Notification<Boolean> updateNotification = authenticationService.updateUserAccount(
						administratorView.getUsernameTextField(), administratorView.getPasswordTextField(), userId);
				if (updateNotification.hasErrors()) {
					JOptionPane.showMessageDialog(administratorView.getContentPane(),
							updateNotification.getFormattedErrors());
				} else {
					if (!updateNotification.getResult()) {
						JOptionPane.showMessageDialog(administratorView.getContentPane(),
								"Update not successful, please try again later.");
					} else {
						JOptionPane.showMessageDialog(administratorView.getContentPane(), "Employee updated");
						updateEmployeesList();
					}
				}
			}
		}
	}

	private class DeleteButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (selectedUser == null) {
				administratorView.setTextAreaText("no employee selected");
		}else {
			 userRepository.delete(selectedUser.getId());
			 updateEmployeesList();
			
		}
		}
	}

	private class ReportButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (selectedUser == null)
				administratorView.setTextAreaText("no employee selected");

		}
	}

	private class TableListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent evt) {
			int row = administratorView.getTable().rowAtPoint(evt.getPoint());
			if (row >= 0) {
				selectedUser = employees.get(row);
				System.out.println("selected user:" + selectedUser.getId() + " " + selectedUser.getUsername()
						+ selectedUser.showListRoles());
				administratorView.setTextAreaText("");
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {}

	}
	}
