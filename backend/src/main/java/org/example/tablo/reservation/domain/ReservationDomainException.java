package org.example.tablo.reservation.domain;

import org.example.tablo.common.exception.DomainException;
import org.example.tablo.common.exception.ErrorCode;

public class ReservationDomainException extends DomainException {

    public ReservationDomainException(ErrorCode errorCode) {
        super(errorCode);
    }
}
