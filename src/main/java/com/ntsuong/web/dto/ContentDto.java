package com.ntsuong.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
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
public class ContentDto {
    private UUID id;

    @NotBlank(message = "Title is blank or empty")
    @NotNull(message = "Tile is required")
    private String title;
    private String content;
    private Boolean isHome;
    private String description;
    private List<String> categoryList;
    private CategoryDto categories;
}
