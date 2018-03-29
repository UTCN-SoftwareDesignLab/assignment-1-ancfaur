package controller;

import static database.Constants.EMPLOYEE_OPERATION_TYPES.*;

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

import model.Account;
import model.Client;
import model.Report;
import model.User;
import model.builders.ReportBuilder;
import model.validation.Notification;
import repository.report.ReportRepository;
import service.account.AccountService;
import service.client.ClientService;
import view.EmployeeMainView;

public class EmployeeController {
	private final EmployeeMainView employeeView;
	private final ClientService clientService;
	private final AccountService accountService;
	private List<Client> clients;
	private List<Account> accounts;
	private Account selectedAccount;
	private Client selectedClient;
	private User user;
	private ReportRepository reportRepository;

	private BillController billController;
	private TransferController transferController;

	public EmployeeController(EmployeeMainView employeeView, ClientService clientService,
			AccountService accountService, ReportRepository reportRepository,
			BillController billController, TransferController transferController) {
		this.employeeView = employeeView;
		this.clientService = clientService;
		this.accountService = accountService;
		this.reportRepository = reportRepository;
		
		this.billController = billController;
		this.transferController = transferController;

		clients = clientService.findAll();

		employeeView.setCreateClientBtnListener(new CreateClientButtonListener());
		employeeView.setUpdateClientBtnListener(new UpdateClientButtonListener());

		employeeView.setCreateAccountBtnListener(new CreateAccountButtonListener());
		employeeView.setUpdateAccountBtnListener(new UpdateAccountButtonListener());
		employeeView.setDeleteAccountBtnListener(new DeleteAccountButtonListener());
		employeeView.setProcessBillBtnListener(new ProcessBillButtonListener());
		employeeView.setTransferBtnListener(new TransferButtonListener());

		employeeView.setClientsTableListener(new ClientsTableListener());
		employeeView.setAccountsTableListener(new AccountsTableListener());

		fillClientTableWithData();
	}

	public void setUser(User user) {
		this.user = user;
		System.out.println("am setat user pe"+user.getUsername()+ "cu id "+ user.getId());
	}

	private void fillClientTableWithData() {
		employeeView.getClientsTableModel().setRowCount(0);
		for (Client client : clients)
			employeeView.getClientsTableModel().addRow(
					new Object[] { client.getId(), client.getName(), client.getCnp(), client.getAddress(), client.getIdCard() });

	}

	private void fillAccountTableWithData() {
		employeeView.getAccountsTableModel().setRowCount(0);
		for (Account account : accounts)
			employeeView.getAccountsTableModel().addRow(new Object[] { account.getId(), account.getBalance(),
					account.getCreationDate(), account.getType() });

	}

	private void updateClientsList() {
		this.clients = clientService.findAll();
		fillClientTableWithData();
	}

	private void updateAccountsList() {
		this.accounts = accountService.findAccountsForClient(selectedClient.getId());
		if (accounts == null)
			System.out.println("no goood brooo");
		if (accounts.size() == 0)
			System.out.println("no accounts to show");
		fillAccountTableWithData();

	}

	private void refreshSelectedAccount() {
		selectedAccount = null;
		employeeView.setBalanceTextField("");
	}

	private Date extractDateFromString(String dateString) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-d");
		Date date;
		try {
			date = df.parse(dateString);
			return date;
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(employeeView.getContentPane(),
					"Please insert dates of format YYYY-MM-DD");
		}
		return null;
	}

	private Report composeReport(String operationType) {
		Report report = new ReportBuilder().setUserId(user.getId())
				.setDate(extractDateFromString(employeeView.getDateTextField()))
				.setClientId(selectedClient.getId())
				.setOperationType(operationType).build();
		
		System.out.println(report.toString());
		return report;
	}

	private class CreateClientButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String cnp = employeeView.getCnpTextField();
			String name = employeeView.getNameTextField();
			String address = employeeView.getAddressTextField();

			Notification<Boolean> registerNotification = clientService.register(cnp, name, address);
			if (registerNotification.hasErrors()) {
				JOptionPane.showMessageDialog(employeeView.getContentPane(), registerNotification.getFormattedErrors());
			} else {
				if (!registerNotification.getResult()) {
					JOptionPane.showMessageDialog(employeeView.getContentPane(),
							"Registration not successful, please try again later.");
				} else {
					JOptionPane.showMessageDialog(employeeView.getContentPane(), "New client registered");
					selectedClient = clientService.findByCnp(cnp);
					Report report = composeReport(CREATE_CLIENT);
					reportRepository.save(report);
					updateClientsList();
				}
			}
		}
	}

	private class UpdateClientButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (selectedClient == null) {
				JOptionPane.showMessageDialog(employeeView.getContentPane(), "No client selected");
			} else {
				Long clientId = selectedClient.getId();
				Notification<Boolean> updateNotification = clientService.updateClient(clientId,
						employeeView.getCnpTextField(), employeeView.getNameTextField(),
						employeeView.getAddressTextField());
				if (updateNotification.hasErrors()) {
					JOptionPane.showMessageDialog(employeeView.getContentPane(),
							updateNotification.getFormattedErrors());
				} else {
					if (!updateNotification.getResult()) {
						JOptionPane.showMessageDialog(employeeView.getContentPane(),
								"Update not successful, please try again later.");
					} else {
						JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client updated");
						Report report = composeReport(UPDATE_CLIENT);
						reportRepository.save(report);
						updateClientsList();
					}
				}
			}
		}
	}

	private class CreateAccountButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (selectedClient == null) {
				JOptionPane.showMessageDialog(employeeView.getContentPane(), "No client selected");
				return;
			}
			if (employeeView.getBalanceTextField().equals("")) {
				JOptionPane.showMessageDialog(employeeView.getContentPane(), "Firstly introduce the amount");
				return;
			}
			Notification<Boolean> notification = accountService.registerAccountToClient(
					Float.parseFloat(employeeView.getBalanceTextField()), employeeView.getTypeCombo(),
					selectedClient.getId());
			if (notification.hasErrors()) {
				JOptionPane.showMessageDialog(employeeView.getContentPane(), notification.getFormattedErrors());
			} else {
				if (!notification.getResult()) {
					JOptionPane.showMessageDialog(employeeView.getContentPane(),
							"Account not created, please try again later.");
				} else {
					JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account created");
					Report report = composeReport(CREATE_ACCOUNT);
					reportRepository.save(report);
					updateAccountsList();
				}
			}

		}
	}

	private class UpdateAccountButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (selectedClient == null || selectedAccount == null) {
				JOptionPane.showMessageDialog(employeeView.getContentPane(), "Please select client, then account");
				return;
			}
			if (employeeView.getBalanceTextField().equals("")) {
				JOptionPane.showMessageDialog(employeeView.getContentPane(), "Firstly introduce the amount");
				return;
			}
			Notification<Boolean> notification = accountService.updateAccount(selectedAccount.getId(),
					Float.parseFloat(employeeView.getBalanceTextField()), employeeView.getTypeCombo());
			if (notification.hasErrors()) {
				JOptionPane.showMessageDialog(employeeView.getContentPane(), notification.getFormattedErrors());
			} else {
				if (!notification.getResult()) {
					JOptionPane.showMessageDialog(employeeView.getContentPane(),
							"Account not updated, please try again later.");
				} else {
					JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account updated");
					Report report = composeReport(UPDATE_ACCOUNT);
					reportRepository.save(report);
					updateAccountsList();
				}
			}
		}

	}

	private class DeleteAccountButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (selectedClient == null || selectedAccount == null) {
				JOptionPane.showMessageDialog(employeeView.getContentPane(), "Please select client, then account");
			} else {
				accountService.delete(selectedClient.getId(), selectedAccount.getId());
				refreshSelectedAccount();
				updateAccountsList();
				Report report = composeReport(DELETE_ACCOUNT);
				reportRepository.save(report);
			}

		}
	}

	private class ProcessBillButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (selectedAccount == null) {
				JOptionPane.showMessageDialog(employeeView.getContentPane(), "Please select  account");
			} else {
				billController.cleanView();
				billController.setAccountId(selectedAccount.getId());
				billController.setClientId(selectedClient.getId());
				billController.setDate(extractDateFromString(employeeView.getDateTextField()));
				billController.setUserId(user.getId());
				billController.setVisible(true);

			}
		}

	}

	private class TransferButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (selectedClient == null || selectedAccount == null) {
				JOptionPane.showMessageDialog(employeeView.getContentPane(), "Please select client then account");
				return;
			}
			transferController.cleanView();
			transferController.setAccountId(selectedAccount.getId());
			transferController.setClientId(selectedClient.getId());
			transferController.setDate(extractDateFromString(employeeView.getDateTextField()));
			transferController.setUserId(user.getId());
			transferController.setVisible(true);

		}
	}

	private class ClientsTableListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent evt) {
			int row = employeeView.getClientsTable().rowAtPoint(evt.getPoint());
			if (row >= 0) {
				selectedClient = clients.get(row);
				System.out.println("selected clients:" + selectedClient.getId() + " " + selectedClient.getName());
				employeeView.setCnpTextField(selectedClient.getCnp());
				employeeView.setNameTextField(selectedClient.getName());
				employeeView.setAddressTextField(selectedClient.getAddress());
				refreshSelectedAccount();
				updateAccountsList();
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

	private class AccountsTableListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent evt) {
			int row = employeeView.getAccountsTable().rowAtPoint(evt.getPoint());
			if (row >= 0) {
				selectedAccount = accounts.get(row);
				System.out.println("selected account:" + selectedAccount.getId() + " " + selectedAccount.getBalance());
				employeeView.setBalanceTextField(new Float(selectedAccount.getBalance()).toString());
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

	public void seVisible(boolean b) {
		employeeView.setVisible(true);
		
	}

}
