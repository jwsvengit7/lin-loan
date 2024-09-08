package com.lin.loanservicemodule.loan.repositories;

import com.lin.loanservicemodule.loan.entities.LoanTenure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanTenureRepository extends JpaRepository<LoanTenure,Long> {
}
