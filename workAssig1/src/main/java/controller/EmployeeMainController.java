package controller;

import static database.Constants.Roles.EMPLOYEE;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.Account;
import model.Client;
import model.Transfer;
import model.User;
import model.builders.AccountBuilder;
import model.validation.Notification;
import repository.account.AccountRepository;
import repository.bill.BillRepository;
import repository.client.ClientRepository;
import repository.user.UserRepository;
import service.account.AccountService;
import service.client.ClientService;
import view.AdministatorMainView;
import view.BillView;
import view.EmployeeMainView;
import view.TransferView;

public class EmployeeMainController {
	private final EmployeeMainView employeeView;
	private final ClientService clientService;
	private final AccountService accountService;
	private List<Client> clients;
	private List<Account> accounts;
	private Account selectedAccount;
	private Client selectedClient;
	private BillRepository billRepository;

	public EmployeeMainController(EmployeeMainView employeeView, ClientService clientService,
			AccountService accountService, BillRepository billRepository) {
		this.employeeView = employeeView;
		this.clientService = clientService;
		this.accountService = accountService;
		this.billRepository = billRepository;

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

	private void fillClientTableWithData() {
		employeeView.getClientsTableModel().setRowCount(0);
		for (Client client : clients)
			employeeView.getClientsTableModel().addRow(
					new Object[] { client.getName(), client.getCnp(), client.getAddress(), client.getIdCard() });

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
			}

		}
	}

	private class ProcessBillButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (selectedAccount == null) {
				JOptionPane.showMessageDialog(employeeView.getContentPane(), "Please select  account");
			} else {
				BillController billController = new BillController(new BillView(), billRepository, selectedAccount, accountService);
			}
		}

	}

	private class TransferButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			TransferController transferController = new TransferController(new TransferView(), accountService);
			if (selectedAccount != null)
				transferController.suggestSourceAccount(selectedAccount.getId());
			accounts = new ArrayList<Account>();
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

}
