package com.mediamarkt.productandcategories.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    @NotNull
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String parentId;
}
