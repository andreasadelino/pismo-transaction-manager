package com.pismo.transactions.resources;

import com.pismo.transactions.dtos.TransactionDTO;
import com.pismo.transactions.services.TransactionService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/transactions")
public class TransactionResource {

    @Inject
    TransactionService transactionService;

    @POST
    public Response process(@Valid TransactionDTO transaction) {
        transactionService.processTransaction(transaction);
        return Response.ok().build();
    }

}
