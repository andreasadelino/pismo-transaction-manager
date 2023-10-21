package com.pismo.transactions.enums;

import java.util.Arrays;

public enum OperationTypes {

    CASH_PURCHASE(1L, "CASH PURCHASE"),
    INSTALLMENT_PURCHASE(2L, "INSTALLMENT PURCHASE"),
    WITHDRAWAL(3L, "WITHDRAWAL"),
    PAYMENT(4L, "PAYMENT"),
    ;

    public final Long id;
    public final String name;

    OperationTypes(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Boolean isValidId(Long id) {
        return Arrays
                .stream(OperationTypes.values())
                .map(it -> it.id)
                .anyMatch(it -> it.equals(id));
    }
}
