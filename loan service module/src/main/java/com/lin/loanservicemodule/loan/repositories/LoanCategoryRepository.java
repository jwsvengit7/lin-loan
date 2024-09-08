package com.lin.loanservicemodule.loan.repositories;

import com.lin.loanservicemodule.loan.entities.LoanCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanCategoryRepository extends JpaRepository<LoanCategory,Long> {
}
