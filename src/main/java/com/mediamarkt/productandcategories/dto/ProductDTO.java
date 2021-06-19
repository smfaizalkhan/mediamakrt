package com.mediamarkt.productandcategories.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @NotNull
    private String name;
    private String category;
    private String onlineStatus;
    private String longDescription;
    private String shortDescription;
    private List<URI> categoryPath;
}
