package com.ntsuong.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 *
 * @author  Nguyen Thanh Suong
 * @version 1.0
 * @since   2022-06-01
 */
@Setter
@Getter
public class AuthorityDto {

    private UUID id;

    @NotNull(message = "Authority is required.")
    @NotBlank(message = "Authority is blank.")
    private String authority;
    private String description;

    @NotNull(message = "Name is required.")
    @NotBlank(message = "Name is blank.")
    private String name;

    private Boolean selected;
}
