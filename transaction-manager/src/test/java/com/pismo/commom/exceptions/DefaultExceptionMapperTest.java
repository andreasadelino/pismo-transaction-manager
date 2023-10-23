package com.pismo.commom.exceptions;

import com.pismo.accounts.services.AccountService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.doThrow;

@QuarkusTest
public class DefaultExceptionMapperTest {

    @InjectMock
    AccountService accountService;

    @Test
    public void forceRuntimeExceptionToTriggerDefaultExceptionMapperTest() {
        doThrow(new RuntimeException("Invalid request."))
            .when(accountService)
            .getById(Mockito.anyLong());

        given()
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
            .when()
            .post("/accounts/1")
            .then()
            .statusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
    }
}
