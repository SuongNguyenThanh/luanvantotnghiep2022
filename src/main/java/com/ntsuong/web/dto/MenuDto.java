package com.ntsuong.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
public class MenuDto {
    private UUID id;

    @NotEmpty(message = "Name is required")
    @NotNull(message = "Name isn't null")
    @NotBlank(message = "Name is Blank")
    private String name;

    private Integer positionIndex;
    private String slug;

    @NotEmpty(message = "Type is required")
    @NotNull(message = "Type isn't null")
    @NotBlank(message = "Type is Blank")
    private String type;

    private String url;
    private String categoryId;
    private String parentId;
    private String contentId;

    private MenuDto parent;
    private List<String> categoryList;
}
