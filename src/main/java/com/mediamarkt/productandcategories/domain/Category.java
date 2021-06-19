package com.mediamarkt.productandcategories.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CATEGORY")
public class Category {

    @Id
    private String id;
    private String name;
    private String parentId;
}
