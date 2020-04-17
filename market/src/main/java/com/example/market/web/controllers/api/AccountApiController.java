package com.example.market.web.controllers.api;

import com.example.market.data.dto.AccountDto;
import com.example.market.data.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/account")
public class AccountApiController {

    private final AccountService accountService;

    @Autowired
    public AccountApiController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public AccountDto one(@PathVariable Integer id) {
        return accountService.findOne(id);
    }
}
