package com.pismo.accounts.services;

import com.pismo.accounts.entities.Account;
import com.pismo.accounts.repositories.AccountRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;

@ApplicationScoped
public class AccountService {

    @Inject
    AccountRepository accountRepository;

    @Transactional
    public void createAccount(Account account) {
        if (accountExists(account)) {
            throw new BadRequestException("Invalid request.");
        }
        accountRepository.persist(account);
    }

    private boolean accountExists(Account account) {
        return accountRepository.count("documentNumber = ?1", account.documentNumber) > 0;
    }

    public Account getById(Long accountId) {
        return accountRepository.findById(accountId);
    }

}
