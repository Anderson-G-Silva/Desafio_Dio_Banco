package br.com.dio.accounts;


import br.com.dio.generalAndExceptions.TransactionsEx;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import static br.com.dio.customers.Customer.scanner;
import static br.com.dio.generalAndExceptions.Validator.emptyData;

public class Transactions {
    public static Transactions transactions = new Transactions();

    private LocalDate date;
    private int account;
    private String type;
    private String name;
    private String history;
    private double value;
    private double previousBalance;
    private double balance;

    public List<Transactions> transactionsList;

    public Transactions(LocalDate date, int account, String type, String name, String history, double value, double previousBalance, double balance) {
        this.date = date;
        this.account = account;
        this.type = type;
        this.name = name;
        this.history = history;
        this.value = value;
        this.previousBalance = previousBalance;
        this.balance = balance;
        this.transactionsList = new ArrayList<>();
    }

    public Transactions() {
        this.transactionsList = new ArrayList<>();
    }

    public static Transactions getTransactions() {
        return transactions;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getAccount() {
        return account;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getHistory() {
        return history;
    }

    public double getValue() {
        return value;
    }

    public double getPreviousBalance() {
        return previousBalance;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "{" +
                "date=" + date +
                ", account=" + account +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", history='" + history + '\'' +
                ", value=" + value +
                ", previousBalance=" + previousBalance +
                ", balance=" + balance +
                '}';
    }

    public Transactions addTransactions (Transactions transactions){
        transactionsList.add(transactions);
        return transactions;
    }

    public void listTransactions(){
        String action = "listTransactions";
        try{
            emptyData(action);
        } catch (TransactionsEx ex){
            System.out.println(ex.getMessage());
            return;
        }
        System.out.println("Informe a conta corrente");
        int findAccount = scanner.nextInt();
        System.out.println("=====> EXTRATO BANCÁRIO <=====");
        transactions.transactionsList.stream()
                .filter(t->  t.getAccount() == findAccount)
                .sorted(Comparator.comparing(Transactions::getDate))
                .forEach(System.out::println);
        System.out.println("===============================");

    }

}
