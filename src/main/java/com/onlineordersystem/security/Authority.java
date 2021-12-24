package com.onlineordersystem.security;

import com.onlineordersystem.domain.Seller;
import com.onlineordersystem.domain.User;
import com.onlineordersystem.domain.base.PrincipleRelated;
import lombok.Getter;

@Getter
public enum Authority {
    SELLER(Seller.class), USER(User.class);

    private final Class<? extends PrincipleRelated> authorityClass;

    Authority(Class<? extends PrincipleRelated> authorityClass) {
        this.authorityClass = authorityClass;
    }
}
