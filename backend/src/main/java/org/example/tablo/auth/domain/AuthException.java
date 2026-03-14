package org.example.tablo.auth.domain;

import org.example.tablo.common.exception.DomainException;
import org.example.tablo.common.exception.ErrorCode;

public class AuthException extends DomainException {

    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
