package br.com.dio.accounts;

import br.com.dio.customers.Customer;


import static br.com.dio.generalAndExceptions.Bank.bank;


public abstract class Account implements IAccount{
    protected static int serial = 1;
    private int agency;
    private int account;
    private double balance;
    private String type;
    protected Customer customer;

    public Account(int agency, int account, double balance, String type, Customer customer) {
        this.agency = bank.getAgency();
        this.account = serial++;
        this.balance = 0;
        this.type = type;
        this.customer = customer;
    }

    public Account() {

    }


    public void setBalance(double balance) {
        this.balance = balance;
    }

    // metodos implementados nas respectivas classes devido as sets em cada uma delas

    @Override
    public void addAccount(Account account){

    }

    @Override
    public void deposit() {

    }

    @Override
    public void withdrawal() {

    }

    @Override
    public void transfer() {

    }


    public int getAgency() {
        return agency;
    }

    public int getAccount() {
        return account;
    }

    public double getBalance() {
        return balance;
    }

    public String getType() {
        return type;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getSerial() {
        return serial;
    }

    @Override
    public String toString() {
        return "Account{" +
                "agency=" + agency +
                ", account=" + account +
                ", balance=" + balance +
                ", type='" + type + '\'' +
                ", customer=" + customer +
                '}';
    }
}
