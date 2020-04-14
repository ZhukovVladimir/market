package com.example.demo.data.dto;

import lombok.Data;

@Data
public class AccountDto {

    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private Long phone;
    private String address;
    private Integer phoneCountryCode;

}
