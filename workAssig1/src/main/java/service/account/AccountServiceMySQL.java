package service.account;

import model.Account;
import model.Client;
import model.builders.AccountBuilder;
import model.builders.ClientBuilder;
import model.validation.AccountValidator;
import model.validation.ClientValidator;
import model.validation.Notification;
import model.validation.Validator;
import repository.EntityNotFoundException;
import repository.account.AccountRepository;

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
    	
    	Account account= new  AccountBuilder()
	                .setBalance(balance)
	                .setCreationDate(creationDate)
	                .setType(type)
	                .build();

	        AccountValidator accountValidator = new AccountValidator(account);
	        boolean accountValid = accountValidator.validate();
	        Notification<Boolean> accountRegisterNotification = new Notification<>();

	        if (!accountValid) {
	        	accountValidator.getErrors().forEach(accountRegisterNotification::addError);
	        	accountRegisterNotification.setResult(Boolean.FALSE);
	        } else {
	        	accountRegisterNotification.setResult(accountRepository.addAccountToClient(account, clientId));
	        }
	        return  accountRegisterNotification;
	}


    
    public List<Account> findAccountsForClient(Long clientId){
    	return accountRepository.findAccountsForClient(clientId);
    }

    
    public Notification<Boolean>  updateAccount(Long accountId, float balance, String type) {
	       Account account= new AccountBuilder()
	    		   .setId(accountId)
	    		   .setBalance(balance)
	    		   .setType(type)
	    		   .build();

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

}