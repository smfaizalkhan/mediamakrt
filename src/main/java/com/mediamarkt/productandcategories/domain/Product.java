package com.mediamarkt.productandcategories.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRODUCT")
public class Product {

    @Id
    private String name;
    private String category;
    @Column(name = "online_status")
    private String onlineStatus;
    @Lob
    @Column(name = "long_description")
    private String longDescription;
    @Column(name = "short_description")
    private String shortDescription;
}
