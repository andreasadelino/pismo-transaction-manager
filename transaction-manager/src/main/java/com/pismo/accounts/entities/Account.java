package com.pismo.accounts.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Immutable;

@Immutable
@Entity
public class Account extends PanacheEntityBase {

    @Id
    @GeneratedValue
    @JsonProperty(value = "account_id")
    public Long id;

    @Size(max = 11, message = "Invalid document!")
    @NotNull(message = "Document Number can't be null.")
    @Column(
        length = 11,
        updatable = false,
        unique = true,
        name = "document_number",
        nullable = false
    )
    @JsonProperty(value = "document_number")
    public String documentNumber;

    public Account() {
    }

    public Account(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Account(long id, String document) {
        this.id = id;
        this.documentNumber = document;
    }
}
