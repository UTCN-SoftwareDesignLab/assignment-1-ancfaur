package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import model.Account;
import model.Bill;
import model.validation.Notification;
import repository.EntityNotFoundException;
import repository.bill.BillRepository;
import service.account.AccountService;
import view.BillView;
import view.TransferView;

public class BillController {

	private BillView billView;
	private AccountService accountService;
	private BillRepository billRepository;
	private Account selectedAccount;
	private Bill selectedBill;

	public BillController(BillView billView, BillRepository billRepository, Account selectedAccount, AccountService accountService) {
		this.billView = billView;
		this.accountService = accountService;
		this.selectedAccount = selectedAccount;
		this.billRepository = billRepository;

		billView.setSearchBtnListener(new SearchButtonListener());
		billView.setProcessBtnListener(new ProcessButtonListener());
		billView.setCancelBtnListener(new CancelButtonListener());

	}

	private class CancelButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			billView.setVisible(false);
			billView.dispose();

		}
	}

	private class SearchButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Long billId = Long.parseLong(billView.getBillIdTextField());
			try {
				selectedBill = billRepository.findById(billId);
			} catch (EntityNotFoundException e1) {
				 JOptionPane.showMessageDialog(billView.getContentPane(), "Bill not found");
			}
			
			billView.setTextArea(selectedBill.toString());
		}
	}

	private class ProcessButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (selectedBill == null) {
				JOptionPane.showMessageDialog(billView.getContentPane(), "Please search for bill firstly");
			} else {
				Notification<Boolean> notification = accountService.processBill(selectedBill, selectedAccount);
				if (notification.hasErrors()) {
					JOptionPane.showMessageDialog(billView.getContentPane(), notification.getFormattedErrors());
				} else {
					if (!notification.getResult()) {
						JOptionPane.showMessageDialog(billView.getContentPane(),
								"Bill payment not successful, please try again later.");
					} else {
						JOptionPane.showMessageDialog(billView.getContentPane(), "Bill payed");
						billRepository.updateStatus(selectedBill);
						billView.setVisible(false);
						billView.dispose();
					}
				}
			}
		}
	}
}
