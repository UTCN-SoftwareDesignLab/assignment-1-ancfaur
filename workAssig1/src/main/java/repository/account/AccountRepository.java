package repository.account;

import model.Account;
import model.Bill;
import model.Client;
import model.Transfer;
import repository.EntityNotFoundException;

import java.util.List;

/**
 * Created by Alex on 07/03/2017.
 */
public interface AccountRepository {

    List<Account> findAll();

    Account findById(Long id) throws EntityNotFoundException;
    
    boolean addAccountToClient(Account account, Long clientId);
    
    List<Account> findAccountsForClient(Long clientId);

    void removeAll();
    
    boolean update(Account account);

	boolean delete(Long clientId, Long accountId);
	
}