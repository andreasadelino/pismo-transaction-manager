package com.pismo.transactions.repositories;

import com.pismo.transactions.entities.OperationType;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OperationTypeRepository implements PanacheRepository<OperationType> {
}
