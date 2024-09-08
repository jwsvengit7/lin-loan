package com.lin.loanservicemodule.loan.repositories;

import com.lin.loanservicemodule.loan.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan,Long> {
}
