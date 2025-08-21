package br.com.dio.generalAndExceptions;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Bank {
    private String bankName = "33-Santander";
    private int agency = 1;
    private String agencyName = "Padrão";

    public String getBankName() {
        return bankName;
    }

    public int getAgency() {
        return agency;
    }

    public String getAgencyName() {
        return agencyName;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "bankName='" + bankName + '\'' +
                ", agency=" + agency +
                ", agencyName='" + agencyName + '\'' +
                '}';
    }

    public static Bank bank = new Bank();


}
