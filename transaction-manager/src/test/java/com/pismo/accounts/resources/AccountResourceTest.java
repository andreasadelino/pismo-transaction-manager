package com.pismo.accounts.resources;

import com.pismo.accounts.entities.Account;
import com.pismo.accounts.services.AccountService;
import com.pismo.commom.exceptions.RequestValidationException;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doThrow;

@QuarkusTest
public class AccountResourceTest {

    private final String document1 = "46969827003";
    private final Account testAccount = new Account(1L, document1);

    @InjectMock
    AccountService accountService;

    @Test
    public void givenAValidAccountPayloadWhenItIsSubmittedThenGetOkStatusCode() {

        given()
            .body(testAccount)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
            .when()
            .post("/accounts")
            .then()
            .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    public void givenAnInvalidAccountPayloadWhenItIsSubmittedThenGetBadRequest() {
        given()
            .body(new Account("12345678901000"))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
            .when()
            .post("/accounts")
            .then()
            .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
            .body("messages", CoreMatchers.hasItem(any(String.class)));
    }

    @Test
    public void givenAnExistentAccountWhenAnAccountWithSameDocumentIdIsSubmittedThenGetBadRequest() {
        doThrow(new RequestValidationException("Invalid request."))
            .when(accountService)
            .createAccount(Mockito.any(Account.class));

        given()
            .body(testAccount)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
            .when()
            .post("/accounts")
            .then()
            .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
            .body(any(String.class));
    }

    @Test
    public void givenAnExistentAccountWhenGetAccountIsCalledThenGetItFindsTheAccount() {
        Mockito.when(accountService.getById(Mockito.anyLong()))
            .thenReturn(testAccount);

        given()
            .body(testAccount)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
            .when()
            .get("/accounts/" + testAccount.id)
            .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .body("account_id", is(testAccount.id.intValue()))
            .body("document_number", is(testAccount.documentNumber));
    }

    @Test
    public void givenAnMissingAccountWhenGetAccountIsCalledThenGetItReturnsNotFound() {
        given()
            .body(testAccount)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
            .when()
            .get("/accounts/" + 99)
            .then()
            .statusCode(Response.Status.NOT_FOUND.getStatusCode());
//            .body("account_id", is(testAccount.id.intValue()))
//            .body("document_number", is(testAccount.documentNumber));
    }
}
