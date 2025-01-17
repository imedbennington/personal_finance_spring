package tn.iteam.springpersofinance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.iteam.springpersofinance.entities.Account;
import tn.iteam.springpersofinance.exceptions.AccountsException;
import tn.iteam.springpersofinance.interfaces.AccountInterface;
import tn.iteam.springpersofinance.repositories.AccountRepository;

import java.util.List;
@Service
public class AccountsService implements AccountInterface {
    @Autowired
    private final AccountRepository accountRepository;

    public AccountsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void createAccount(Account account) throws AccountsException {
        try{
            accountRepository.save(account);
        }catch (AccountsException e){
            throw new AccountsException(e.getMessage());
        }
    }

    @Override
    public void updateAccount(Account account, Long id) throws AccountsException {

    }

    @Override
    public void deleteAccount(Long id) throws AccountsException {

    }

    @Override
    public Account getAccount(Long id) throws AccountsException {
        try{
            return accountRepository.findById(id).get();
        } catch (Exception e) {
            throw new AccountsException(e.getMessage());
        }
    }

    @Override
    public List<Account> getAccounts() throws AccountsException {
        try{
            return accountRepository.findAll();
        }catch (AccountsException e){
            throw new AccountsException(e.getMessage());
        }
    }
}
