package br.com.dio.accounts;

import br.com.dio.customers.Customer;
import br.com.dio.generalAndExceptions.TransactionsEx;
import br.com.dio.generalAndExceptions.Validator;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import static br.com.dio.customers.Customer.customers;
import static br.com.dio.customers.Customer.scanner;

public class CurrentAccount extends Account{
    public Set<CurrentAccount> currentAccountsSet;

    public CurrentAccount(int agency, int account, double balance, String type, Customer customer) {
        super(agency, account, balance, type, customer);
        this.currentAccountsSet = new HashSet<>();
    }

      public CurrentAccount() {
        super();
        this.currentAccountsSet = new HashSet<>();
    }

    String type = "CC";


    //Metodo adicionar conta e cliente
    public void addAccount(CurrentAccount currentAccount) {
        if (customer == null){
            System.out.println("Processo não concluídos - dados inválidos\n");
        }
        else {
            currentAccountsSet.add(currentAccount);
            System.out.println("Conta cadastrada com sucesso");
        }
    }
    // metodo dados para adicionar conta
    public CurrentAccount requestToSave(){
        int agency;
        int account;
        double balance;
        //String type = "CC";
        customer = customers.addCustomer(customers.requestToSave());
        if (customer == null){
            return null;
        }
        else{
        CurrentAccount currentAccount = new CurrentAccount(0,0,0,type,customer);
        return (currentAccount);
        }
    }
    //Metodo lista dados da conta
    public void listAccount() {
        String action = "listAccountsCC";
        try{
            Validator.emptyData(action);
        }
        catch (TransactionsEx ex){
            System.out.println(ex.getMessage());
            return;
        }
        System.out.println("=====>RELAÇÃO DE CONTA CORRENTE<=====");
        currentAccountsSet.stream()
                .forEach(System.out::println);
        System.out.println(currentAccount.getAccount());
        System.out.println("======================================");
    }
    // metodo depósito
    public void deposit(){
        String action = "depositCC";
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
        for(CurrentAccount cc: currentAccountsSet){
            if(cc.getAccount() == findAccount){
                double previousBalance = cc.getBalance();
                checkAccount = cc.getAccount();
                cc.setBalance(cc.getBalance()+value);
                System.out.printf("Depósito de %.2f realizado com sucesso\n", value);
                Transactions transactions = new Transactions(LocalDate.now(),checkAccount,"CC",cc.getCustomer().getName(),"depósito",value,previousBalance, cc.getBalance());
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
        String action = "withdrawalCC";
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
        for(CurrentAccount cc: currentAccountsSet){
            if(cc.getAccount() == findAccount){
                double previousBalance = cc.getBalance();
                try{Validator.values(value);
                Validator.balance(previousBalance,value);}
                catch (TransactionsEx ex){
                    System.out.println(ex.getMessage());
                    return;
                }
                checkAccount = cc.getAccount();
                cc.setBalance(cc.getBalance()-value);
                System.out.printf("Saque de %.2f realizado com sucesso\n", value);
                Transactions transactions = new Transactions(LocalDate.now(),checkAccount,type,cc.getCustomer().getName(),"saque",value,previousBalance, cc.getBalance());
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
        String action = "transferCC";
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
       boolean result1 = currentAccountsSet.stream()
               .anyMatch(cc->cc.getAccount() == findAccount);
        boolean result2 = currentAccountsSet.stream()
                .anyMatch(cc->cc.getAccount() == findAccountD);
        boolean result3 = SavingAccount.savingAccount.savingAccountSet.stream()
                .anyMatch(pp->pp.getAccount() == findAccountD);
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
            for(CurrentAccount cc: currentAccountsSet) {
                if (cc.getAccount() == findAccount) {
                    double previousBalance = cc.getBalance();
                    try {
                        Validator.balance(previousBalance, value);
                    }
                    catch (TransactionsEx ex) {
                        System.out.println(ex.getMessage());
                        return;
                    }
                    cc.setBalance(cc.getBalance() - value);
                    Transactions transactions = new Transactions(LocalDate.now(),cc.getAccount(),"CC",cc.getCustomer().getName(),"Transferência saída para conta -"+findAccountD,value,previousBalance, cc.getBalance());
                    Transactions.transactions.addTransactions(transactions);
                    break;
                }
            }
        }
        if (result1 == true && result2 == true){
            // efetiva operação destino CC
            for(CurrentAccount cc: currentAccountsSet) {
                if (cc.getAccount() == findAccountD) {
                    double previousBalance = cc.getBalance();
                    cc.setBalance(cc.getBalance() + value);
                    System.out.printf("Transferência de %.2f para conta %d realizado com sucesso\n", value,findAccountD);
                    Transactions transactions = new Transactions(LocalDate.now(),cc.getAccount(),"CC",cc.getCustomer().getName(),"Transferência entrada da conta -"+findAccount,value,previousBalance, cc.getBalance());
                    Transactions.transactions.addTransactions(transactions);
                    break;
                }
            }
        }
        if (result1 == true && result3 == true){
            // efetiva operação destino PP
            for(SavingAccount pp: SavingAccount.savingAccount.savingAccountSet) {
                if (pp.getAccount() == findAccountD) {
                    double previousBalance = pp.getBalance();
                    pp.setBalance(pp.getBalance() + value);
                    System.out.printf("Transferência de %2f para conta %d realizado com sucesso\n", value,findAccountD);
                    Transactions transactions = new Transactions(LocalDate.now(),pp.getAccount(),"CC",pp.getCustomer().getName(),"Transferência entrada da conta -"+findAccount,value,previousBalance, pp.getBalance());
                    Transactions.transactions.addTransactions(transactions);
                    break;
                }
            }
        }
    }
    public static CurrentAccount currentAccount = new CurrentAccount();
}
