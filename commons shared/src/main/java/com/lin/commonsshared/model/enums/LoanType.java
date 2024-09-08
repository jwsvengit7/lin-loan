package com.lin.commonsshared.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LoanType {
    SCHOOL_FESS("005",""),
    BUSINESS_LOAN("003",""),
    ATOM_LOAN("002",""),
    LIN_LOAN("001","");

    private final String code;
    private final String description;
}
