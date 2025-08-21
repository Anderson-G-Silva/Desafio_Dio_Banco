package br.com.dio.generalAndExceptions;

import br.com.dio.customers.Customer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static br.com.dio.accounts.CurrentAccount.currentAccount;
import static br.com.dio.accounts.SavingAccount.savingAccount;
import static br.com.dio.accounts.Transactions.transactions;
import static br.com.dio.customers.Customer.customers;

// Validação de transações
public class Validator {
    public static void emptyData(String action) throws TransactionsEx{
        if((customers.customerSet.isEmpty() && action.equalsIgnoreCase("listCustomer"))
        || transactions.transactionsList.isEmpty() && action.equalsIgnoreCase("listTransactions"))
            throw new TransactionsEx("Banco de dados vazio");
        if ((currentAccount.currentAccountsSet.isEmpty()
                && (action.equalsIgnoreCase("depositCC")
                || action.equalsIgnoreCase("withdrawalCC")
                || action.equalsIgnoreCase("transferCC")
                || action.equalsIgnoreCase("listAccountsCC")))

                || (savingAccount.savingAccountSet.isEmpty()
                && (action.equalsIgnoreCase("depositPP")
                || action.equalsIgnoreCase("withdrawalPP")
                || action.equalsIgnoreCase("transferPP")
                || action.equalsIgnoreCase("listAccountsPP"))))
            throw new TransactionsEx("Banco de dados vazio");

    }

    public static void values(double value) throws TransactionsEx{
        if (value <= 0)
            throw new TransactionsEx("ERRO! Valor inválido - informe um valor positivo\n");
    }
    public static void balance(double previousBalance, double value) throws TransactionsEx{
        if (previousBalance - value < 0)
            throw new TransactionsEx("ERRO! Saldo insuficiente");
    }

  // Validação de clientes
    public static void checkCPF(String digitinf, String digit) throws CustomersEx{
        if (!digitinf.equalsIgnoreCase(digit))
            throw new CustomersEx("ERRO! CPF inválido");
    }

    public static void checkCPF1(int size) throws CustomersEx{
        if (size!=11)
            throw new CustomersEx("ERRO! CPF inválido");
    }

    public static void checkCustomer(Customer customer) throws CustomersEx{
        if (stringIsBlank(customer.getName()))
            throw new CustomersEx("ERRO! Informe um nome válido\nReinicie o processo\n");
        if (customer.getName().length() <=1)
            throw new CustomersEx(("ERRO! O nome deve ter mais de um caractér\nReinicie o processo\n"));
        if (stringIsBlank(customer.getEmail()))
            throw new CustomersEx("ERRO! Informe um e-mail válido\nReinicie o processo\n");
        if (!customer.getEmail().contains("@") || !customer.getEmail().contains("."))
            throw new CustomersEx("ERRO! Informe um e-mail válido\nReinicie o processo\n");
    }

    private static boolean stringIsBlank (final String value) {
        return value == null || value.isBlank();
    }

    public static String validadorCPF(String CPF){
        String digits[]=CPF.split("");
        LinkedList<Integer> digit_1 = Arrays.stream(digits)
                .flatMapToInt(String::chars)
                .map(Character::getNumericValue)
                .limit(9)
                .boxed()
                .collect(Collectors.toCollection(LinkedList::new));
        List<Integer> multiplier1 = Arrays.asList(10,9,8,7,6,5,4,3,2);
        List<Integer> multiplier2 = Arrays.asList(11,10,9,8,7,6,5,4,3);

        double sumDig_1 = IntStream.range(0,9)
                .map(i-> digit_1.get(i)*multiplier1.get(i))
                .sum();
        double digit1 = 11-sumDig_1%11;
        if(digit1>10) {digit1 = 0;}
        double sumDig_2 = IntStream.range(0,9)
                .map(i-> digit_1.get(i)*multiplier2.get(i))
                .sum();
        double digit2 = 11-(sumDig_2+digit1*2)%11;
        if(digit2>10) {digit2 = 0;}
        String digit = String.valueOf((int)Math.round(digit1))+String.valueOf((int)Math.round(digit2));
        return digit;
    }
}
