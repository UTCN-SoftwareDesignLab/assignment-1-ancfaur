package service.account;

import model.Account;
import model.Bill;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.util.List;

public interface AccountService {
	

    List<Account> findAll();

    Account findById(Long id) throws EntityNotFoundException;

    List<Account> findAccountsForClient(Long clientId);
    
    Notification<Boolean> registerAccountToClient(float balance, String type,  Long clientId);
    
    Notification<Boolean>  updateAccount(Long accountId, float balance, String type);
    
    boolean delete(Long clientId, Long accountId);
    
    Notification<Boolean> transfer(Long sourceId, Long destinationId, float amount);
    
    Notification<Boolean> processBill(Bill bill, Account account);

}