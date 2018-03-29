package controller;

import static database.Constants.EMPLOYEE_OPERATION_TYPES.TRANSFER;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.JOptionPane;
import model.Report;
import model.builders.ReportBuilder;
import model.validation.Notification;
import repository.report.ReportRepository;
import service.account.AccountService;
import view.TransferView;

public class TransferController {

	private TransferView transferView;
	private AccountService accountService;
	private ReportRepository reportRepository;
	private Long clientId;
	private Long accountId;
	private Date date;
	private Long userId;
	
	
	public TransferController(TransferView transferView, AccountService accountService, ReportRepository reportRepository) {
		this.transferView = transferView;
		this.accountService = accountService;
		this.reportRepository = reportRepository;

		
		transferView.setOkBtnListener(new OkButtonListener());
		transferView.setCancelBtnListener(new CancelButtonListener());
		
	}
	
	public void cleanView() {
		transferView.setDestTextField("");
		transferView.setAmountTextField("");
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
		transferView.setClientTextField(clientId.toString());
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
		transferView.setSourceTextField(accountId.toString());
	}

	public void setDate(Date date) {
		this.date = date;
		transferView.setDateTextField(date.toString());
	}

	
	private class OkButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Notification<Boolean> notification = accountService.transfer(accountId, Long.parseLong(transferView.getDestTextField()), Float.parseFloat(transferView.getAmountTextField()));
			if (notification.hasErrors()) {
				JOptionPane.showMessageDialog(transferView.getContentPane(), notification.getFormattedErrors());
			} else {
				if (!notification.getResult()) {
					JOptionPane.showMessageDialog(transferView.getContentPane(),
							"Transfer not successful, please try again later.");
				} else {
					JOptionPane.showMessageDialog(transferView.getContentPane(), "Transfer executed!");
					Report report = new ReportBuilder()
							.setUserId(userId)
							.setDate(date)
							.setClientId(clientId)
							.setOperationType(TRANSFER)
							.build();
					reportRepository.save(report);
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

	public void setVisible(boolean b) {
		transferView.setVisible(true);
		
	}
	
	
}
