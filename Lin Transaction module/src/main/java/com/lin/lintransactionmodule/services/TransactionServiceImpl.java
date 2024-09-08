package com.lin.lintransactionmodule.services;

import com.lin.lintransactionmodule.entity.Transaction;
import com.lin.lintransactionmodule.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl {
    private final TransactionRepository transactionRepository;

    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }
    public List<Transaction> getTransaction() {
        return List.of(new Transaction());
    }
}
