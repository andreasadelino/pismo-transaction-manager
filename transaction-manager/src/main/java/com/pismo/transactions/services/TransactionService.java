package com.pismo.transactions.services;

import com.pismo.accounts.entities.Account;
import com.pismo.accounts.services.AccountService;
import com.pismo.commom.exceptions.RequestValidationException;
import com.pismo.transactions.dtos.TransactionDTO;
import com.pismo.transactions.entities.OperationType;
import com.pismo.transactions.entities.Transaction;
import com.pismo.transactions.enums.OperationTypes;
import com.pismo.transactions.repositories.OperationTypeRepository;
import com.pismo.transactions.repositories.TransactionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TransactionService {

    public static final String INVALID_REQUEST = "Invalid request";

    @Inject
    OperationTypeRepository operationTypeRepository;

    @Inject
    AccountService accountService;

    @Inject
    TransactionRepository transactionRepository;

    @Transactional
    public void processTransaction(TransactionDTO transaction) {
        final var operation = getOperationType(transaction);
        final var account = getAccount(transaction);

        final var newTransaction = new Transaction(account, operation, transaction.amount);

        transactionRepository.persist(newTransaction);
    }

    private Account getAccount(TransactionDTO transaction) {
        var account = accountService.getById(transaction.accountId);
        if (account == null) {
            throw new RequestValidationException(INVALID_REQUEST + ": Account does not exist.");
        }
        return account;
    }

    private OperationType getOperationType(TransactionDTO transaction) {
        if (!OperationTypes.isValidId(transaction.operationTypeId)) {
            throw new RequestValidationException(INVALID_REQUEST + ": Operation Type does not exist.");
        }
        return operationTypeRepository.findById(transaction.operationTypeId);
    }
}
