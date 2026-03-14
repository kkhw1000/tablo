package org.example.tablo.user.domain;

import org.example.tablo.common.exception.DomainException;
import org.example.tablo.common.exception.ErrorCode;

public class UserDomainException extends DomainException {

    public UserDomainException(ErrorCode errorCode) {
        super(errorCode);
    }
}
