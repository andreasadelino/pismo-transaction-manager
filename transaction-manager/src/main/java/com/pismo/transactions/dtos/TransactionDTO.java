package com.pismo.transactions.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class TransactionDTO {
    @NotNull(message = "'account_id can't be null.")
    @JsonProperty(value = "account_id")
    public Long accountId;

    @NotNull(message = "'operation_type_id' can't be null.")
    @JsonProperty(value = "operation_type_id")
    public Long operationTypeId;

    @Min(value = 0, message = "'amount' can't be negative.")
    @NotNull(message = "'amount' can't be null.")
    public double amount;
}
