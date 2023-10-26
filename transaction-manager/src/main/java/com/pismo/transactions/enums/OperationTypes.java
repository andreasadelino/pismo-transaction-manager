package com.pismo.transactions.enums;

import java.util.Arrays;

public enum OperationTypes {

    CASH_PURCHASE(1L, "CASH PURCHASE", -1),
    INSTALLMENT_PURCHASE(2L, "INSTALLMENT PURCHASE", -1),
    WITHDRAWAL(3L, "WITHDRAWAL", -1),
    PAYMENT(4L, "PAYMENT", 1),
    ;

    public final Long id;
    public final String name;
    public final int multiplier;

    OperationTypes(Long id, String name, int multiplier) {
        this.id = id;
        this.name = name;
        this.multiplier = multiplier;
    }

    public static Boolean isValidId(Long id) {
        return Arrays
                .stream(OperationTypes.values())
                .map(it -> it.id)
                .anyMatch(it -> it.equals(id));
    }

    public static int operationMultiplier(Long operationTypeId) {
        final OperationTypes operationType = Arrays
                .stream(OperationTypes.values())
                .filter(it -> it.id.equals(operationTypeId))
                .findFirst()
                .orElseThrow();

        return operationType.multiplier;
    }

    public static OperationTypes forId(Long operationTypeId) {
        return Arrays
            .stream(OperationTypes.values())
            .filter(it -> it.id.equals(operationTypeId))
            .findFirst()
            .orElseThrow();
    }

    public Boolean isNegative() {
        return this.multiplier < 0;
    }
}
