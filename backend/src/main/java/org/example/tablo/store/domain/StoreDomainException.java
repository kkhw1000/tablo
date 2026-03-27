package org.example.tablo.store.domain;

import org.example.tablo.common.exception.DomainException;
import org.example.tablo.common.exception.ErrorCode;

public class StoreDomainException extends DomainException {

    public StoreDomainException(ErrorCode errorCode) {
        super(errorCode);
    }
}
