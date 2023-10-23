package com.pismo.accounts.resources;

import com.pismo.accounts.entities.Account;
import com.pismo.accounts.services.AccountService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/accounts")
public class AccountResource {

    @Inject
    AccountService accountService;

    @POST
    public Response createAccount(@Valid Account account) {
        accountService.createAccount(account);
        return Response.ok().build();
    }

    @Path("/{accountId}")
    @GET
    public Response getAccount(
        @NotNull
        @PathParam(value = "accountId")
        Long accountId
    ) {
        final Account account = accountService.getById(accountId);

        if (account == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(account).build();
    }
}
