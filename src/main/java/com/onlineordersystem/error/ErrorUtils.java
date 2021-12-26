package com.onlineordersystem.error;

import com.onlineordersystem.model.ErrorResultDTO;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class ErrorUtils {

    private ErrorUtils() {
    }

    public static Map<String, List<ErrorResultDTO>> getResponseBody(List<ErrorResultDTO> errorList) {
        return Collections.singletonMap("errors", errorList);
    }

}
