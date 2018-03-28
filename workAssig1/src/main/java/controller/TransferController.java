package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.Transfer;
import model.validation.Notification;
import service.account.AccountService;
import view.TransferView;

public class TransferController {

	private TransferView transferView;
	private AccountService accountService;

	
	public TransferController(TransferView transferView, AccountService accountService) {
		this.transferView = transferView;
		this.accountService = accountService;

		
		transferView.setOkBtnListener(new OkButtonListener());
		transferView.setCancelBtnListener(new CancelButtonListener());
		
	}
	
	public void suggestSourceAccount(Long sourceId) {
		transferView.setSourceTextField(sourceId.toString());
	}
	
	private class OkButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Notification<Boolean> notification = accountService.transfer(Long.parseLong(transferView.getSourceTextField()), Long.parseLong(transferView.getDestTextField()), Float.parseFloat(transferView.getAmountTextField()));
			if (notification.hasErrors()) {
				JOptionPane.showMessageDialog(transferView.getContentPane(), notification.getFormattedErrors());
			} else {
				if (!notification.getResult()) {
					JOptionPane.showMessageDialog(transferView.getContentPane(),
							"Transfer not successful, please try again later.");
				} else {
					JOptionPane.showMessageDialog(transferView.getContentPane(), "Transfer executed!");
					transferView.setVisible(false); 
					transferView.dispose(); 
				}
		
		}
	}
	}
	
	private class CancelButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			transferView.setVisible(false);
			transferView.dispose(); 
			
		}
	}
	
	
}
