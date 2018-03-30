package service.account;

import model.Account;
import model.Bill;
import model.BillPayment;
import model.Transfer;
import model.builders.AccountBuilder;
import model.builders.TransferBuilder;
import model.validation.AccountValidator;
import model.validation.BillPaymentValidator;
import model.validation.Notification;
import model.validation.TransferValidator;
import model.validation.Validator;
import repository.EntityNotFoundException;
import repository.account.AccountRepository;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AccountServiceMySQL implements AccountService {

	private final AccountRepository accountRepository;

	public AccountServiceMySQL(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public List<Account> findAll() {
		return accountRepository.findAll();
	}

	@Override
	public Account findById(Long id) throws EntityNotFoundException {
		return accountRepository.findById(id);
	}

	public Notification<Boolean> registerAccountToClient(float balance, String type, Long clientId) {
		Date creationDate = Calendar.getInstance().getTime();

		Account account = new AccountBuilder().setBalance(balance).setCreationDate(creationDate).setType(type).build();

		AccountValidator accountValidator = new AccountValidator(account);
		boolean accountValid = accountValidator.validate();
		Notification<Boolean> accountRegisterNotification = new Notification<>();

		if (!accountValid) {
			accountValidator.getErrors().forEach(accountRegisterNotification::addError);
			accountRegisterNotification.setResult(Boolean.FALSE);
		} else {
			accountRegisterNotification.setResult(accountRepository.addAccountToClient(account, clientId));
		}
		return accountRegisterNotification;
	}

	public List<Account> findAccountsForClient(Long clientId) {
		return accountRepository.findAccountsForClient(clientId);
	}

	public Notification<Boolean> updateAccount(Long accountId, float balance, String type) {
		Account account = new AccountBuilder().setId(accountId).setBalance(balance).setType(type).build();

		Validator accountValidator = new AccountValidator(account);
		boolean accountValid = accountValidator.validate();
		Notification<Boolean> notification = new Notification<>();
		if (!accountValid) {
			accountValidator.getErrors().forEach(notification::addError);
			notification.setResult(Boolean.FALSE);
		} else {
			notification.setResult(accountRepository.update(account));
		}
		return notification;
	}

	@Override
	public boolean delete(Long clientId, Long accountId) {
		return accountRepository.delete(clientId, accountId);
	}

	private List<Account> getTransactionAccounts(Long sourceId, Long destinationId) {
		List<Account> accounts = new ArrayList<Account>();
		Account source;
		Account dest;
		try {
			source = findById(sourceId);
			dest = findById(destinationId);
			accounts.add(source);
			accounts.add(dest);
			return accounts;
		} catch (EntityNotFoundException e) {
			return null;
		}

	}

	public Notification<Boolean> transfer(Long sourceId, Long destinationId, float amount) {
		Notification<Boolean> transferNotification = new Notification<>();
		List<Account> foundAccounts = getTransactionAccounts(sourceId, destinationId);
		if (foundAccounts == null) {
			transferNotification.addError("You have introduced inexistant id accounts");
			transferNotification.setResult(Boolean.FALSE);
		} else {
			Transfer transfer = new TransferBuilder().setSource(foundAccounts.get(0))
					.setDestination(foundAccounts.get(1)).setAmount(amount).build();

			TransferValidator transferValidator = new TransferValidator(transfer);
			boolean transferValid = transferValidator.validate();

			if (!transferValid) {
				transferValidator.getErrors().forEach(transferNotification::addError);
				transferNotification.setResult(Boolean.FALSE);
			} else {
				Account source = transfer.getSource();
				Account dest = transfer.getDestination();
				source.setBalance(source.getBalance() - transfer.getAmount());
				dest.setBalance(dest.getBalance()+ transfer.getAmount());
				transferNotification.setResult(accountRepository.update(source) && accountRepository.update(dest));
			}

		}
		return transferNotification;
	}
	
	public Notification<Boolean> processBill(Bill bill, Account account){
		BillPayment billPayment = new BillPayment(bill, account);
		Validator validator = new BillPaymentValidator(billPayment);
		boolean valid = validator.validate();
		Notification<Boolean> notification = new Notification<>();
		if (!valid) {
			validator.getErrors().forEach(notification::addError);
			notification.setResult(Boolean.FALSE);
		} else {
		    account.setBalance(account.getBalance() - bill.getAmount());
			notification.setResult(accountRepository.update(account));
		}
		return notification;
	}
	
	public void removeAll() {
		accountRepository.removeAll();
	}

}