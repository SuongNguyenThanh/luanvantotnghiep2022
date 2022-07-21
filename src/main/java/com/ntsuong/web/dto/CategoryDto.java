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
@Getter
@Setter
public class CategoryDto {
    private UUID id;
    private String description;

    @NotNull(message = "Name is Null")
    @NotBlank(message = "Name is Blank")
    private String name;

    private String slug;
    private String url;
    private String parentId;
    private Boolean selected;
    private Boolean isHome;

    private CategoryDto parent;
}
