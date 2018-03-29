package controller;

import static database.Constants.EMPLOYEE_OPERATION_TYPES.PROCESS_BILL;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JOptionPane;
import model.Bill;
import model.Report;
import model.builders.ReportBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;
import repository.bill.BillRepository;
import repository.report.ReportRepository;
import service.account.AccountService;
import view.BillView;

public class BillController {

	private BillView billView;
	private AccountService accountService;
	private BillRepository billRepository;
	private ReportRepository reportRepository;
	private Bill selectedBill;
	private Long clientId;
	private Long accountId;
	private Date date;
	private Long userId;

	public BillController(BillView billView, BillRepository billRepository, AccountService accountService,
			ReportRepository reportRepository) {
		this.billView = billView;
		this.accountService = accountService;
		this.billRepository = billRepository;
		this.reportRepository = reportRepository;

		billView.setSearchBtnListener(new SearchButtonListener());
		billView.setProcessBtnListener(new ProcessButtonListener());
		billView.setCancelBtnListener(new CancelButtonListener());

	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
		billView.setClientTextField(clientId.toString());
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
		billView.setAccountTextField(accountId.toString());
	}

	public void setDate(Date date) {
		this.date = date;
		billView.setDateTextField(date.toString());
	}
	
	public void setVisible(boolean val) {
		billView.setVisible(val);
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
				try {
					Notification<Boolean> notification = accountService.processBill(selectedBill,
							accountService.findById(accountId));
					if (notification.hasErrors()) {
						JOptionPane.showMessageDialog(billView.getContentPane(), notification.getFormattedErrors());
					} else {
						if (!notification.getResult()) {
							JOptionPane.showMessageDialog(billView.getContentPane(),
									"Bill payment not successful, please try again later.");
						} else {
							JOptionPane.showMessageDialog(billView.getContentPane(), "Bill payed");
							billRepository.updateStatus(selectedBill);
							Report report = new ReportBuilder().setUserId(userId).setClientId(clientId).setDate(date)
									.setClientId(clientId).setOperationType(PROCESS_BILL).build();
							reportRepository.save(report);
							billView.setVisible(false);
							billView.dispose();
						}
					}

				} catch (EntityNotFoundException e1) {

					e1.printStackTrace();
				}
			}
		}
	}


	public void cleanView() {
		billView.setBillTextField("");	
	}
}
