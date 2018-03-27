package service.account;

import model.Account;
import repository.EntityNotFoundException;

import java.util.List;

public interface AccountService {

    List<Account> findAll();

    Account findById(Long id) throws EntityNotFoundException;

    boolean  addAccountoClient(Account account, Long clientId);

    float getDaysPassed(Long id) throws EntityNotFoundException;

}