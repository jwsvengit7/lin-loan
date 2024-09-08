package com.lin.lintransactionmodule.repository;

import com.lin.lintransactionmodule.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
