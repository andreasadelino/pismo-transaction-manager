package com.pismo.transactions.entities;

import com.pismo.accounts.entities.Account;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@Immutable
@Entity
public class Transaction extends PanacheEntity {

    @ManyToOne
    public Account account;

    @ManyToOne
    public OperationType operationType;

    @CreationTimestamp
    public double amount;

    @Column(name = "event_date")
    public LocalDateTime eventDate;
}
