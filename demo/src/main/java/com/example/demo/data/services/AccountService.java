package com.example.demo.data.services;

import com.example.demo.data.models.Account;
import com.example.demo.data.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account findOne(Integer id) {
        return accountRepository.findById(id).get();
    }
}
