package com.onlineordersystem.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerDTO {

    private String businessName;
    private String businessAddress;
    private PrincipleDTO principle;

}
