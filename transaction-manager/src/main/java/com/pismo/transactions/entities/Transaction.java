package com.pismo.transactions.entities;

import com.pismo.accounts.entities.Account;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.SourceType;

import java.time.LocalDateTime;

@Immutable
@Entity
public class Transaction extends PanacheEntity {

    @ManyToOne
    public Account account;

    @JoinColumn(name = "operationtype_id", nullable = false)
    @ManyToOne
    public OperationType operationType;

    @Column(nullable = false)
    public double amount;

    @CreationTimestamp(source = SourceType.VM)
    @Column(name = "event_date", nullable = false)
    public LocalDateTime eventDate;

    public Transaction() {
    }

    public Transaction(Account account, OperationType operation, double amount) {
        this.account = account;
        this.operationType = operation;
        this.amount = amount;
    }
}
