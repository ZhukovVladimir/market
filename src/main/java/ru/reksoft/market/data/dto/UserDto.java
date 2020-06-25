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
    @NotNull(message = "{password.notnull}")
    @NotEmpty(message = "{password.notempty}")
    @JsonIgnore
    private String password;

    @NotNull(message = "{username.notnull}")
    @NotEmpty(message = "{username.notempty}")
    @Email
    private String username;

    @NotNull(message = "{firstName.notnull}")
    @NotEmpty(message = "{firstName.notempty}")
    private String firstName;

    @NotNull(message = "{lastName.notnull}")
    @NotEmpty(message = "{lastName.notempty}")
    private String lastName;

    @NotNull(message = "{phone.notnull}")
    private Long phone;

    @NotNull(message = "{address.notnull}")
    @NotEmpty(message = "{address.notempty}")
    private String address;

    @NotNull(message = "{phoneCountryCode.notnull}")
    private Integer phoneCountryCode;

    @Null(message = "{authorities.null}")
    private Set<AuthorityDto> authorities;
}
