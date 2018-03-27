package service.account;

import model.Account;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.util.List;

public interface AccountService {

    List<Account> findAll();

    Account findById(Long id) throws EntityNotFoundException;

    List<Account> findAccountsForClient(Long clientId);
    
    Notification<Boolean> registerAccountToClient(float balance, String type,  Long clientId);
    
    Notification<Boolean>  updateAccount(Long accountId, float balance, String type);

}