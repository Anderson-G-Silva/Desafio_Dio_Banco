
import br.com.dio.accounts.Transactions;

import java.util.Scanner;

import static br.com.dio.accounts.SavingAccount.savingAccount;
import static br.com.dio.accounts.Transactions.transactions;
import static br.com.dio.customers.Customer.customers;
import static br.com.dio.accounts.CurrentAccount.currentAccount;


public class Main {
    //public final static Transactions transactions = new Transactions();
    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        int option = 0;
        while (option != 4) {
            System.out.println("1 - Menu conta corrente");
            System.out.println("2 - Menu conta poupança");
            System.out.println("3 - Listar clientes");
            System.out.println("4 - Sair");
            option = scanner.nextInt();
            switch (option) {
                case 1 -> menuCC();
                case 2 -> menuPP();
                case 3 -> customers.listCustomer();
                case 4 -> {
                    break;
                }
                default -> System.out.println("Opção inválida");

            }
        }
    }

    public static void menuCC() {
        int option = 0;
        while (option != 7) {
            System.out.println("1 - Abrir conta corrente");
            System.out.println("2 - Depositar");
            System.out.println("3 - Sacar");
            System.out.println("4 - transferir");
            System.out.println("5 - extrato");
            System.out.println("6 - listar clientes");
            System.out.println("7 - Sair");
            option = scanner.nextInt();
            switch (option) {
                case 1 -> currentAccount.addAccount(currentAccount.requestToSave());
                case 2 -> currentAccount.deposit();
                case 3 -> currentAccount.withdrawal();
                case 4 -> currentAccount.transfer();
                case 5 -> transactions.listTransactions();
                case 6 -> currentAccount.listAccount();
                case 7 -> {
                    break;
                }
                default -> System.out.println("Opção inválida");
            }
        }
    }

    public static void menuPP() {
        int option = 0;
        while (option != 7) {
            System.out.println("1 - Abrir conta poupança");
            System.out.println("2 - Depositar");
            System.out.println("3 - Sacar");
            System.out.println("4 - transferir");
            System.out.println("5 - extrato");
            System.out.println("6 - listar clientes");
            System.out.println("7 - Sair");
            option = scanner.nextInt();
            switch (option) {
                case 1 -> savingAccount.addAccount(savingAccount.requestToSave());
                case 2 -> savingAccount.deposit();
                case 3 -> savingAccount.withdrawal();
                case 4 -> savingAccount.transfer();
                case 5 -> transactions.listTransactions();
                case 6 -> savingAccount.listAccounts();
                case 7 -> {
                    break;
                }
                default -> System.out.println("Opção inválida");

            }
        }
    }
}
