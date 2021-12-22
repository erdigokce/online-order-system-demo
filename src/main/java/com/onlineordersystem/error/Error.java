package com.onlineordersystem.error;

import java.io.Serializable;

public interface Error extends Serializable {

    String getMessageKey();

}
