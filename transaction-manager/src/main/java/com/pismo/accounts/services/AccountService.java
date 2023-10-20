package com.pismo.accounts.services;

import com.pismo.accounts.entities.Account;
import com.pismo.accounts.repositories.AccountRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AccountService {

    @Inject
    AccountRepository accountRepository;

    @Transactional
    public void createAccount(Account account) {
        // Todo - validate if document exists before save
        accountRepository.persist(account);
    }
}
