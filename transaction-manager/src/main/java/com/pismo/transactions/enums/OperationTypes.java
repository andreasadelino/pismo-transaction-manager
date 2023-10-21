package com.pismo.transactions.enums;

import java.util.Arrays;

public enum OperationTypes {

    CASH_PURCHASE(1L, "CASH PURCHASE", 1, false),
    INSTALLMENT_PURCHASE(2L, "INSTALLMENT PURCHASE", 1, false),
    WITHDRAWAL(3L, "WITHDRAWAL", -1, true),
    PAYMENT(4L, "PAYMENT", -1, true),
    ;

    public final Long id;
    public final String name;
    public final int multiplier;
    public final Boolean negativeOperation;

    OperationTypes(Long id, String name, int multiplier, Boolean negativeOperation) {
        this.id = id;
        this.name = name;
        this.multiplier = multiplier;
        this.negativeOperation = negativeOperation;
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

//        return operationType.negativeOperation ? -1 : 1;
        return operationType.multiplier;
    }
}
