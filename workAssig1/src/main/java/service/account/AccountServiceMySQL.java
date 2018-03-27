package service.account;

import model.Account;
import repository.EntityNotFoundException;
import repository.account.AccountRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Alex on 07/03/2017.
 */
public class AccountServiceMySQL implements AccountService {

    private final AccountRepository repository;

    public AccountServiceMySQL(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Account> findAll() {
        return repository.findAll();
    }

    @Override
    public Account findById(Long id) throws EntityNotFoundException {
        return repository.findById(id);
    }

    @Override
    public boolean addAccountoClient(Account account, Long clientId) {
        boolean success= repository.addAccountToClient(account, clientId);
        return success;
    }

    @Override
    public float getDaysPassed(Long id) throws EntityNotFoundException {
        Account account = findById(id);
        Date creationDate = account.getCreationDate();

        Date now = Calendar.getInstance().getTime();
        
        return (now.getTime() - creationDate.getTime())/(24 * 60 * 60 * 1000);
    }


}