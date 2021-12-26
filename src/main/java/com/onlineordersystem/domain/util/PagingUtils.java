package com.onlineordersystem.domain.util;

import java.util.Optional;
import org.springframework.data.domain.PageRequest;

public class PagingUtils {

    private PagingUtils() {
    }

    public static PageRequest getPageRequest(Integer page, Integer size) {
        Integer pageSize = Optional.ofNullable(size).orElse(10);
        Integer pageIndex = Optional.ofNullable(page).orElse(0);
        return PageRequest.of(pageIndex, pageSize);
    }
}
