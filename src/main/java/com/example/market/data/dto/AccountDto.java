package com.example.market.data.dto;

import lombok.Data;

@Data
public class AccountDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Long phone;
    private String address;
    private Integer phoneCountryCode;

}
