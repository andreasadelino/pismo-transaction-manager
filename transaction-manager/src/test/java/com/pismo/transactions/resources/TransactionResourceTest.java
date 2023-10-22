package com.pismo.transactions.resources;

import com.pismo.transactions.dtos.TransactionDTO;
import com.pismo.transactions.enums.OperationTypes;
import com.pismo.transactions.services.TransactionService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class TransactionResourceTest {

    @InjectMock
    TransactionService transactionService;

    @Test
    public void givenAValidTransactionPayloadWhenItIsSubmittedThenItReturnsOk() {
        final var transaction = new TransactionDTO(
            1L,
            OperationTypes.CASH_PURCHASE.id,
            5421.25
        );

        given()
            .body(transaction)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
            .when()
            .post("/transactions")
            .then()
            .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    public void givenAnInvalidTransactionPayloadWhenItIsSubmittedThenItReturnsOk() {
        final var transaction = new TransactionDTO(
            1L,
            5L,
            -5421.25
        );

        given()
            .body(transaction)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
            .when()
            .post("/transactions")
            .then()
            .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
            .body("messages", CoreMatchers.hasItems());
    }
}
