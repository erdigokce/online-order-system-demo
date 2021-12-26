package com.onlineordersystem.model;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

    private UUID id;
    private String name;
    private String description;
    private int quantity;
    private String sellerBusinessName;
}
