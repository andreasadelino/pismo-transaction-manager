package com.pismo.accounts.resources;

import com.pismo.accounts.entities.Account;
import com.pismo.accounts.services.AccountService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/accounts")
public class AccountResource {

    @Inject
    AccountService accountService;

    @POST
    public void createAccount(@Valid Account account) {
        accountService.createAccount(account);
    }
}
