package com.example.market.data.services;

import com.example.market.data.dto.AccountDto;
import com.example.market.data.repositories.AccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public AccountService(AccountRepository accountRepository, ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }

    public AccountDto findOne(Integer id) {
        return modelMapper.map(
                accountRepository.findById(id).get(),
                AccountDto.class);
    }
}
