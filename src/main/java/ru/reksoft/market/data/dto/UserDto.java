package ru.reksoft.market.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Set;

@Data
public class UserDto {
    @NotNull
    @NotEmpty
    @JsonIgnore
    private String password;

    @NotNull
    @NotEmpty
    @Email
    private String username;

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    private Long phone;

    @NotNull
    @NotEmpty
    private String address;

    @NotNull
    private Integer phoneCountryCode;

    @Null
    private Set<AuthorityDto> authorities;
}
