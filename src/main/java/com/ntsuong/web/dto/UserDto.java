package com.ntsuong.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author  Nguyen Thanh Suong
 * @version 1.0
 * @since   2022-06-01
 */
@Getter
@Setter
public class UserDto {
    private UUID id;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private String firstName;
    private String lastName;
    private String middleName;
    private String avatarUrl;
    private String address;
    private String phone;
    private String career;

    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;

    private boolean enabled;

    @NotBlank(message = "Password is blank")
    @NotNull(message = "Password is required")
    @Size(min = 8, message = "Password must be 8 characters or more")
    private String password;

    @NotBlank(message = "Username is blank")
    @NotNull(message = "Username is required")
    private String username;

    private UUID authorityId;

    private List<AuthorityDto> authorityList;
}
