package com.lin.commonsshared.api;

import lombok.Getter;
/**
 *  Temple Jack Chiorlu
 *
 */
@Getter
public class ApiEndpoints {

    /**
     *
     *  Loan module route
     *
     */
    public final static String LOAN_BASEURL="/api/loan";
    public final static String LOAN_SCHEDULE="/schedule-loan";
    public final static String ALL_LOAN="/all-loan";
    public final static String LOAN_STATISTIC="/statistic";
    public final static String LOAN_REPORT="/report";
    public final static String LOAN="/loan";

    /**
     *
     *  Authentication module route
     *
     */
    public final static String AUTH_BASEURL="/api/auth";
    public final static String AUTH_SIGNUP="/signup";
    public final static String AUTH_LOGIN="/login";
    public final static String AUTH_VERIFY_OTP="/verify-otp";
    public final static String AUTH_RESEND_OTP="/resend-otp";

}
