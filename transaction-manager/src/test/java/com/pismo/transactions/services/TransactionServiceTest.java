package com.pismo.transactions.services;

import com.pismo.accounts.entities.Account;
import com.pismo.accounts.repositories.AccountRepository;
import com.pismo.commom.exceptions.RequestValidationException;
import com.pismo.transactions.dtos.TransactionDTO;
import com.pismo.transactions.entities.Transaction;
import com.pismo.transactions.enums.OperationTypes;
import com.pismo.transactions.repositories.TransactionRepository;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

@QuarkusTest
public class TransactionServiceTest {

    private final String document1 = "46969827003";
    private final Account testAccount = new Account(document1);
    private final double amount = 238.85;

    @Inject
    AccountRepository accountRepository;

    @Inject
    TransactionRepository transactionRepository;

    @Inject
    TransactionService sut;

    @Test
    @TestTransaction
    public void givenAValidTransactionPayloadWhenItTriesToProcessThenItSavesSuccessfuly() {
        // Given
        final Account account = mockAccount();
        final long transactionsBefore = countTransactionsByAccount(account);
        final TransactionDTO transactionPayload = new TransactionDTO(
            account.id,
            OperationTypes.CASH_PURCHASE.id,
            amount
        );

        // When
        sut.processTransaction(transactionPayload);

        // Then
        final List<Transaction> transactionsAfter = getTransactionsByAccount(account);
        final Transaction transaction = transactionsAfter.get(0);
        Assertions.assertEquals(transactionsBefore + 1, transactionsAfter.size());
        Assertions.assertEquals(account, transaction.account);
        Assertions.assertEquals(OperationTypes.CASH_PURCHASE.id, transaction.operationType.id);
        Assertions.assertEquals(-amount, transaction.amount);
        Assertions.assertNotNull(transaction.eventDate);
    }

    @Test
    @TestTransaction
    public void givenAValidTransactionWithInexistentAccountPayloadWhenItTriesToProcessThenAnExceptionIsThrown() {
        // Given
        final long transactionsBefore = transactionRepository.count();
        final TransactionDTO transactionPayload = new TransactionDTO(
            99L,
            OperationTypes.CASH_PURCHASE.id,
            amount
        );

        // When
        Assertions.assertThrows(RequestValidationException.class, () -> sut.processTransaction(transactionPayload));

        // Then
        final long transactionsAfter = transactionRepository.count();
        Assertions.assertEquals(transactionsBefore, transactionsAfter);
    }

    @Test
    @TestTransaction
    public void givenAValidTransactionWithInexistentOperationTypePayloadWhenItTriesToProcessThenAnExceptionIsThrown() {
        // Given
        final Account account = mockAccount();
        final TransactionDTO transactionPayload = new TransactionDTO(
            account.id,
            99L,
            amount
        );

        // When
        Assertions.assertThrows(RequestValidationException.class, () -> sut.processTransaction(transactionPayload));

        // Then
        final List<Transaction> transactionsAfter = getTransactionsByAccount(account);
        Assertions.assertEquals(0, transactionsAfter.size());
    }

    @TestTransaction
    Account mockAccount() {
        accountRepository.persist(testAccount);
        return accountRepository.find("documentNumber = ?1", document1).firstResult();
    }

    private List<Transaction> getTransactionsByAccount(Account account) {
        return transactionRepository.find("account.id = ?1", account.id).list();
    }

    private long countTransactionsByAccount(Account account) {
        return transactionRepository.count("account.id = ?1", account.id);
    }
}
