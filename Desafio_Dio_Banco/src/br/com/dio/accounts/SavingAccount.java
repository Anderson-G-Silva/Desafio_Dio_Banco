package br.com.dio.accounts;

import br.com.dio.customers.Customer;
import br.com.dio.generalAndExceptions.TransactionsEx;
import br.com.dio.generalAndExceptions.Validator;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import static br.com.dio.customers.Customer.customers;
import static br.com.dio.customers.Customer.scanner;

public class SavingAccount extends Account {

    public Set<SavingAccount> savingAccountSet;

    public SavingAccount(int agency, int account, double balance, String type, Customer customer) {
        super(agency, account, balance, type, customer);
        this.savingAccountSet = new HashSet<>();
    }

    public SavingAccount() {
        super();
        this.savingAccountSet = new HashSet<>();
    }

    String type = "PP";


    //Metodo adicionar conta e cliente
    public void addAccount(SavingAccount savingAccount) {
        if (customer == null){
            System.out.println("Processo não concluídos - dados inválidos\n");
        }
        else {
            savingAccountSet.add(savingAccount);
            System.out.println("Conta cadastrada com sucesso");
        }
    }
    // metodo dados para adicionar conta
    public SavingAccount requestToSave(){
        int agency;
        int account;
        double balance;
        //String type = "CC";
        customer = customers.addCustomer(customers.requestToSave());
        if (customer == null){
            return null;
        }
        else{
        SavingAccount savingAccount  = new SavingAccount(0,0,0,type,customer);
        return (savingAccount);
        }
    }
    //Metodo lista dados da conta
    public void listAccounts() {
        String action = "listAccountsPP";
        try{
            Validator.emptyData(action);
        }
        catch (TransactionsEx ex){
            System.out.println(ex.getMessage());
            return;
        }
        System.out.println("=====>RELAÇÃO DE CONTA POUPANÇA<=====");
        savingAccountSet.stream()
                .forEach(System.out::println);
        System.out.println(savingAccount.getAccount());
        System.out.println("======================================");
    }
    // metodo depósito
    public void deposit(){
        String action = "depositPP";
        try{
            Validator.emptyData(action);
        }
        catch (TransactionsEx ex){
            System.out.println(ex.getMessage());
            return;
        }
        System.out.println("Informe a conta:");
        int findAccount = scanner.nextInt();
        System.out.println("Informe o valor");
        double value = scanner.nextDouble();
        try{
            Validator.values(value);}
        catch (TransactionsEx ex){
            System.out.println(ex.getMessage());
            return;
        }
        int checkAccount = 0;
        for(SavingAccount pp: savingAccountSet){
            if(pp.getAccount() == findAccount){
                double previousBalance = pp.getBalance();
                checkAccount = pp.getAccount();
                pp.setBalance(pp.getBalance()+value);
                System.out.printf("Depósito de %.2f realizado com sucesso\n", value);
                Transactions transactions = new Transactions(LocalDate.now(),checkAccount,"CC",pp.getCustomer().getName(),"depósito",value,previousBalance, pp.getBalance());
                Transactions.transactions.addTransactions(transactions);
                break;
            }
        }
        if(checkAccount != findAccount){
            System.out.println("Conta inexistente\n");
        }

    }

    // metodo saque
    public void withdrawal() {
        String action = "withdrawalPP";
        try{
            Validator.emptyData(action);
        }
        catch (TransactionsEx ex){
            System.out.println(ex.getMessage());
            return;
        }
        System.out.println("Informe a conta:");
        int findAccount = scanner.nextInt();
        System.out.println("Informe o valor");
        double value = scanner.nextDouble();
        int checkAccount = 0;
        for(SavingAccount pp: savingAccountSet){
            if(pp.getAccount() == findAccount){
                double previousBalance = pp.getBalance();
                try{Validator.values(value);
                    Validator.balance(previousBalance,value);}
                catch (TransactionsEx ex){
                    System.out.println(ex.getMessage());
                    return;
                }
                checkAccount = pp.getAccount();
                pp.setBalance(pp.getBalance()-value);
                System.out.printf("Saque de %.2f realizado com sucesso\n", value);
                Transactions transactions = new Transactions(LocalDate.now(),checkAccount,type,pp.getCustomer().getName(),"saque",value,previousBalance, pp.getBalance());
                Transactions.transactions.addTransactions(transactions);
                break;
            }
        }
        if(checkAccount != findAccount){
            System.out.println("Conta inexistente\n");
        }
    }

    //metodo transferência
    public void transfer() {
        //Verifica se banco dados está vazio
        String action = "transferPP";
        try{
            Validator.emptyData(action);
        }
        catch (TransactionsEx ex){
            System.out.println(ex.getMessage());
            return;
        }
        // interação com usuário
        System.out.println("Informe a conta de origem:");
        int findAccount = scanner.nextInt();
        System.out.println("Informe a conta de destino:");
        int findAccountD = scanner.nextInt();
        System.out.println("Informe o valor");
        double value = scanner.nextDouble();
        if(findAccount == findAccountD){
            System.out.println("ERRO! Conta de origem e destino são iguais\n");
            return;
        }
        //Verifica valor se negativo ou zero
        try{
            Validator.values(value);
        }
        catch (TransactionsEx ex){
            System.out.println(ex.getMessage());
            return;
        }
        //verifica se as contas existem
        boolean result1 = savingAccountSet.stream()
                .anyMatch(pp->pp.getAccount() == findAccount);
        boolean result2 = savingAccountSet.stream()
                .anyMatch(pp->pp.getAccount() == findAccountD);
        boolean result3 = CurrentAccount.currentAccount.currentAccountsSet.stream()
                .anyMatch(cc->cc.getAccount() == findAccountD);
        if (result1 == false){
            System.out.println("ERRO! Conta de origem inexistente\n");
        }
        else if (result2 == false && result3 == false){
            System.out.println("ERRO! Conta de destino inexistente\n");
        }
        else{
            ;
        }
        if (result1 == true && (result2 == true || result3 == true)){
            // efetiva operação origem
            for(SavingAccount pp: savingAccountSet) {
                if (pp.getAccount() == findAccount) {
                    double previousBalance = pp.getBalance();
                    try {
                        Validator.balance(previousBalance, value);
                    }
                    catch (TransactionsEx ex) {
                        System.out.println(ex.getMessage());
                        return;
                    }
                    pp.setBalance(pp.getBalance() - value);
                    Transactions transactions = new Transactions(LocalDate.now(),pp.getAccount(),"CC",pp.getCustomer().getName(),"Transferência saída para conta -"+findAccountD,value,previousBalance, pp.getBalance());
                    Transactions.transactions.addTransactions(transactions);
                    break;
                }
            }
        }
        if (result1 == true && result2 == true){
            // efetiva operação destino PP
            for(SavingAccount pp: savingAccountSet) {
                if (pp.getAccount() == findAccountD) {
                    double previousBalance = pp.getBalance();
                    pp.setBalance(pp.getBalance() + value);
                    System.out.printf("Transferência de %.2f para conta %d realizado com sucesso\n", value,findAccountD);
                    Transactions transactions = new Transactions(LocalDate.now(),pp.getAccount(),"CC",pp.getCustomer().getName(),"Transferência entrada da conta -"+findAccount,value,previousBalance, pp.getBalance());
                    Transactions.transactions.addTransactions(transactions);
                    break;
                }
            }
        }
        if (result1 == true && result3 == true){
            // efetiva operação destino CC
            for(CurrentAccount cc: CurrentAccount.currentAccount.currentAccountsSet) {
                if (cc.getAccount() == findAccountD) {
                    double previousBalance = cc.getBalance();
                    cc.setBalance(cc.getBalance() + value);
                    System.out.printf("Transferência de %2f para conta %d realizado com sucesso\n", value,findAccountD);
                    Transactions transactions = new Transactions(LocalDate.now(),cc.getAccount(),"CC",cc.getCustomer().getName(),"Transferência entrada da conta -"+findAccount,value,previousBalance, cc.getBalance());
                    Transactions.transactions.addTransactions(transactions);
                    break;
                }
            }
        }
    }
    public static SavingAccount savingAccount = new SavingAccount();
}

