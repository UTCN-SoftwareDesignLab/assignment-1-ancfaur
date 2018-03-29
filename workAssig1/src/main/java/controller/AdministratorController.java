package controller;

import static database.Constants.Roles.EMPLOYEE;

import model.Report;
import model.User;
import model.validation.Notification;
import repository.report.ReportRepository;
import repository.user.UserRepository;
import service.user.AuthenticationService;
import view.AdministatorMainView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

public class AdministratorController {
	private final AdministatorMainView administratorView;
	private final UserRepository userRepository;
	private final AuthenticationService authenticationService;
	private final ReportRepository reportRepository;
	private List<User> employees;
	private User selectedUser;

	public AdministratorController(AdministatorMainView administratorView, UserRepository userRepository,
			AuthenticationService authenticationService, ReportRepository reportRepository) {
		this.administratorView = administratorView;
		this.userRepository = userRepository;
		this.authenticationService = authenticationService;
		this.reportRepository = reportRepository;
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
		for (User user : employees) {
			administratorView.getTableModel().addRow(new Object[] { user.getUsername(), user.showListRoles() });
		}
	}

	private void updateEmployeesList() {
		this.employees = userRepository.findAllWithRole("employee");
		fillJtableWithData();
	}

	private Date extractDateFromString(String dateString) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-d");
		Date date;
		try {
			date = df.parse(dateString);
			return date;
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(administratorView.getContentPane(),
					"Please insert dates of format YYYY-MM-DD");
		}
		return null;
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
				JOptionPane.showMessageDialog(administratorView.getContentPane(), "No user selected");
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
				JOptionPane.showMessageDialog(administratorView.getContentPane(), "No user selected");
			} else {
				userRepository.delete(selectedUser.getId());
				updateEmployeesList();
				refreshSelectedUser();

			}
		}
	}

	private void refreshSelectedUser() {
		selectedUser = null;
		administratorView.setUsernameTextField("");
		administratorView.setPasswordTextField("");
	}

	private class ReportButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (selectedUser == null) {
				JOptionPane.showMessageDialog(administratorView.getContentPane(), "No user selected");
				return;
			}
			Date startDate = extractDateFromString(administratorView.getStartDateTextField());
			Date endDate = extractDateFromString(administratorView.getEndDateTextField());
			List<Report> reports = reportRepository.findReports(selectedUser.getId(), startDate, endDate);
			String allReports = "";
			for (Report report : reports) {
				allReports += report.toString();
			}
			administratorView.setTextAreaText(allReports);

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
				administratorView.setUsernameTextField(selectedUser.getUsername());
				administratorView.setPasswordTextField("********");
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
		public void mouseExited(MouseEvent e) {
		}

	}

	public void setVisible(boolean b) {
		administratorView.setVisible(b);

	}

	

}
