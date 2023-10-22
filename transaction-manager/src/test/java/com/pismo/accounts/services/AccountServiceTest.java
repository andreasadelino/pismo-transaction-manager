package com.pismo.accounts.services;

import com.pismo.accounts.entities.Account;
import com.pismo.accounts.repositories.AccountRepository;
import com.pismo.commom.exceptions.RequestValidationException;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class AccountServiceTest {

    private final String document1 = "46969827003";
    private final Account testAccount = new Account(document1);

    @Inject
    AccountRepository accountRepository;

    @Inject
    AccountService accountService;

    @Test
    @TestTransaction
    public void createAccountTest() {
        //Given
        final long accountsBefore = accountRepository.count();

        // When
        accountService.createAccount(testAccount);

        // Then
        final Account newAccount = accountRepository.listAll().get(0);
        Assertions.assertEquals(accountRepository.count(), accountsBefore + 1);
        Assertions.assertEquals(newAccount.documentNumber, document1);
    }

    @Test
    @TestTransaction
    public void givenAnExistentAccountWhenItTryToAddANewAccountWithSameDocumentAnExceptionIsThrown() {
        //Given
        mockAccount();
        final long accountsBefore = accountRepository.count();

        // When
        Assertions.assertThrows(RequestValidationException.class, () -> accountService.createAccount(testAccount));

        // Then
        final Account newAccount = accountRepository.listAll().get(0);
        Assertions.assertEquals(accountRepository.count(), accountsBefore);
    }

    @Test
    @TestTransaction
    public void givenAnExistentAccountWhenGetAccountIsCalledThenTheAccountIsReturned() {
        //Given
        mockAccount();

        // When
        final Account mockedAccount = accountRepository.find("documentNumber = ?1", document1).firstResult();
        final Account account = accountService.getById(mockedAccount.id);

        // Then
        Assertions.assertNotNull(account);
        Assertions.assertEquals(document1, account.documentNumber);
    }

    @Transactional
    void mockAccount() {
        accountRepository.persist(testAccount);
    }
}
