package tn.iteam.springpersofinance.interfaces;

import tn.iteam.springpersofinance.dto.AccountDto;
import tn.iteam.springpersofinance.entities.Account;
import tn.iteam.springpersofinance.exceptions.AccountsException;

import java.util.List;


public interface AccountInterface {
    void createAccount(Account account) throws AccountsException;
    void updateAccount(Account account, Long id) throws AccountsException;
    void deleteAccount(Long id) throws AccountsException;
    Account getAccount(Long id) throws AccountsException;
    List<Account> getAccounts() throws AccountsException;
}
