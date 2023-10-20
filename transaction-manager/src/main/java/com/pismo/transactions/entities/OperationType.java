package com.pismo.transactions.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import org.hibernate.annotations.Immutable;

@Immutable
@Entity
public class OperationType extends PanacheEntity {

    public String description;
}
