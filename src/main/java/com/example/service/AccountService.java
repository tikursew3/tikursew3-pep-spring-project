package com.example.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;

/**
     * This is a Service class.
     * 
     */
@Service
public class AccountService {
    AccountRepository accountRepository;
      
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    /**
     * This Method is getAccountById.
     * @param accountId the id of an account
     * @return an object of Account if exist null otherwise.
     */
    public Account getAccountById(Integer accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if(optionalAccount.isPresent()) {
            return optionalAccount.get();
        } else {
            return null;
        }     
    }
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }
     /**
     * This Method is findAccountByUsername which finds an account with given username.
     * @param username the username of an account
     * @return true if an account with specified username exist false otherwise.
     */
    public boolean findAccountByUsername(String username) {
        Account account = accountRepository.findAccountByUsername(username);
        if (account.getUsername().equals(username)) {
            return true;
        } else {
            return false;
        }   
    }
    /**
     * This Method is registerAccount which registers new account.
     * @param account  an object of account class
     * @return an object of Account  if exist null otherwise.
     */
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
    
      /**
     * This Method is loginAccount which checks the login logic.
     * @param username  username of an account which try to login
     * @param password password of an account which try to login
     * @return an object of Account  if successfully login null otherwise.
     */
    public Account loginAccount(String username,  String password) {
        for(Account account : getAllAccount()) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                return account;
            }
        } 
        return null;
    }
}
