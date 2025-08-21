package br.com.dio.customers;

import br.com.dio.generalAndExceptions.CustomersEx;
import br.com.dio.generalAndExceptions.TransactionsEx;
import br.com.dio.generalAndExceptions.Validator;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class Customer {
    public static Scanner scanner = new Scanner(System.in);

    private String name;
    private String CPF;
    private String email;

    public Customer(String name, String CPF, String email) {
        this.name = name;
        this.CPF = CPF;
        this.email = email;
    }

    public Set<Customer> customerSet;


    public Customer() {
        this.customerSet = new HashSet<>();
    }



    public String getName() {
        return name;
    }

    public String getCPF() {
        return CPF;
    }

    public String getEmail() {
        return email;
    }

    public Set<Customer> getCustomerSet() {
        return customerSet;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(CPF, customer.CPF);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(CPF);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", CPF='" + CPF + '\'' +
                ", email='" + email+
                '}';
    }

    public Customer addCustomer (Customer customers){
        customerSet.add(customers);
        return customers;
    }

    public Customer requestToSave(){
        System.out.println("Informe o CPF cliente - sem pontos e traço:");
        String CPF = scanner.next();
        String finalCPF = CPF;
        int size = CPF.length();
        try{Validator.checkCPF1(size);}
        catch (CustomersEx ex){
            System.out.println(ex.getMessage());
            return null;
        }
        String digitInf = CPF.substring(CPF.length()-2);
        String digit = Validator.validadorCPF(CPF);
        try{
            Validator.checkCPF(digitInf,digit);
        } catch ( StringIndexOutOfBoundsException |CustomersEx ex){
            System.out.println(ex.getMessage());
            return null;
        }
        if (!customerSet.isEmpty()){
            List<Customer> findCustomer = customerSet.stream()
                    .filter(c->c.getCPF().equalsIgnoreCase(finalCPF))
                    .toList();
            if (!findCustomer.isEmpty()){
                CPF = findCustomer.get(0).getCPF();
                String name = findCustomer.get(0).getName();
                String email = findCustomer.get(0).getEmail();
                System.out.println("CPF cadastrado - conta será aberta dos os dados existente\n"+"CPF: "+CPF+"\n"+"Nome: "+name+"\n"+"E-mail: "+email);
                var customer = new Customer(name,CPF,email);
                return customer;
            }
        }
        scanner.nextLine();
        System.out.println("Informe o nome completo do cliente:");
        name = scanner.nextLine();
        System.out.println("Informe o e-mail cliente:");
        email = scanner.next();
        var customer = new Customer(name,CPF,email);
        try{
            Validator.checkCPF(digitInf,digit);
            Validator.checkCustomer(customer);
        } catch ( StringIndexOutOfBoundsException |CustomersEx ex){
            System.out.println(ex.getMessage());
            return null;
        }
        return (customer);
    }

    public void listCustomer(){
        String action = "listCustomer";
        try{
            Validator.emptyData(action);
        }
        catch (TransactionsEx ex)
        {
            System.out.println(ex.getMessage());
            return;
        }
        System.out.println("=====> RELAÇÃO DE CLIENTES <=====");
        customerSet.stream()
                .sorted(Comparator.comparing(Customer::getName))
                .forEach(System.out::println);
        System.out.println("=================================");
    }

    public static Customer customers = new Customer();
}



