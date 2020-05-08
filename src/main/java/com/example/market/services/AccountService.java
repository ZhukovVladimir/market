package com.example.market.services;

import com.example.market.data.dto.AccountDto;
import com.example.market.data.repositories.AccountRepository;
import com.example.market.exceptions.ResourceNotFoundException;
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

    public AccountDto findOne(Long id) {
        return modelMapper.map(
                accountRepository.findById(id).orElseThrow(ResourceNotFoundException::new),
                AccountDto.class);
    }
}
