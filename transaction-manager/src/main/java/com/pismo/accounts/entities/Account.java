package com.pismo.accounts.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pismo.commom.exceptions.RequestValidationException;
import com.pismo.transactions.dtos.TransactionDTO;
import com.pismo.transactions.entities.Transaction;
import com.pismo.transactions.enums.OperationTypes;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "account")
public class Account extends PanacheEntityBase {

    @Id
    @GeneratedValue
    @JsonProperty(value = "account_id")
    public Long id;

    @Size(max = 11, message = "Invalid document!")
    @NotNull(message = "Document Number can't be null.")
    @Column(
        length = 11,
        updatable = false,
        unique = true,
        name = "document_number",
        nullable = false
    )
    @JsonProperty(value = "document_number")
    public String documentNumber;

    @Min(value = 0, message = "'available_credit_limit' can't be negative.")
    @Column(name = "available_credit_limit")
    @JsonProperty("available_credit_limit")
    public double availableCreditLimit;

    public Account() {
    }

    public Account(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Account(long id, String document) {
        this.id = id;
        this.documentNumber = document;
    }

    @Transactional
    public void processTransaction(TransactionDTO transaction) {

        final OperationTypes operationType = OperationTypes.forId(transaction.operationTypeId);

        // 5000 max
        // 2500
        // 10000

        if (operationType.isNegative() &&
            this.availableCreditLimit - transaction.amount < 0
        ) {

            throw new RequestValidationException("Transaction denied");
        }

        this.availableCreditLimit = this.availableCreditLimit + (operationType.multiplier * transaction.amount);
    }
}
