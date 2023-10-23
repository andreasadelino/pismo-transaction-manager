package com.pismo.transactions.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;

@Immutable
@Entity
@Table(name = "operation_type")
public class OperationType extends PanacheEntity {

    public String description;

    public OperationType() {
    }
}
