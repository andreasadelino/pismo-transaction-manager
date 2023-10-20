package com.pismo.accounts.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Immutable;

@Immutable
@Entity
public class Account extends PanacheEntity {

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
}
