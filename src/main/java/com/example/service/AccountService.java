package com.example.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    AccountRepository accountRepository;
      
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    public Account getAccountById(Integer id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if(optionalAccount.isPresent()) {
            return optionalAccount.get();
        } else {
            return null;
        }     
    }
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }
    public boolean findAccountByUsername(String username) {
        Account account = accountRepository.findAccountByUsername(username);
        if (account.getUsername().equals(username)) {
            return true;
        } else {
            return false;
        }   
    }
    public Account registerAccount(Account account) {
        if(accountRepository.findAccountByUsername(account.getUsername()) == null) {
            if (!account.getUsername().isBlank()) {
                if (account.getPassword().length() >=4) {
                    Account registeredAccount = accountRepository.save(account);
                    return registeredAccount;
                }
            }
        }
        return null;
    }
    public Account loginAccount(String username,  String password) {
        for(Account account : getAllAccount()) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                return account;
            }
        } 
        return null;
    }
}
