/**
 * @author Ozan_Beyaztunc
 * @version 06.04.2022
 */

import java.util.ArrayList;
import java.util.Random;

public class Assignment02_20170808045 {
    public static void main(String[] args) {
 
    }
}
class Bank{
    private String name;
    private String address;
    ArrayList <Customer> Customers=new ArrayList<>();
    ArrayList <Company> Companies=new ArrayList<>();
    ArrayList <Account> Accounts=new ArrayList<>();

    public Bank(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public void addCustomer(int id,String name,String surname){
        Customers.add(new Customer(id,name,surname));
    }
    public void addCompany(int id, String name){
        Companies.add(new Company(id,name));
    }
    public void addAccount(Account account){
        Accounts.add(account);
    }
    public Customer getCustomer(int id) throws CustomerNotFoundException{
        for (int i = 0; i < Customers.size(); i++) {
            if (Customers.get(i).getId()==id){
                return Customers.get(i);
            }
        }
        throw new CustomerNotFoundException(id);
    }
    public Customer getCustomer(String name,String surname) throws
            CustomerNotFoundException {
        for (int i = 0; i < Customers.size(); i++) {
            if (Customers.get(i).getName().equals(name) && //"or" or "and" idk
                    Customers.get(i).getSurname().equals(surname)){
                return Customers.get(i);
            }
        }
        throw new CustomerNotFoundException(name,surname);
    }
    public Company getCompany(int id) throws CompanyNotFoundException {
        for (int i = 0; i < Companies.size(); i++) {
            if (Companies.get(i).getId()==id){
                return Companies.get(i);
            }
        }
        throw new CompanyNotFoundException(id);
    }
    public Company getCompany(String name) throws CompanyNotFoundException{
        for (int i = 0; i < Companies.size(); i++) {
            if (Companies.get(i).getName().equals(name)){
                return Companies.get(i);
            }
        }
        throw new CompanyNotFoundException(name);
    }
    public Account getAccount(String accountNum) throws
            AccountNotFoundException {
        for (int i = 0; i < Accounts.size(); i++) {
            if (Accounts.get(i).getAcctNum().equals(accountNum)){
                return Accounts.get(i);
            }
        }
        throw new AccountNotFoundException(accountNum);
    }
    public double transferFunds(String accountFrom,String accountTo,
        double amount) throws InvalidAmountException, AccountNotFoundException{

        Account account_from = getAccount(accountFrom);
        Account account_to = getAccount(accountTo);

        account_from.withdrawal(amount);
        account_to.deposit(amount);

        return amount;
    }
    public void closeAccount(String accountNum) throws BalanceRemainingException,
            AccountNotFoundException {
        for (int i = 0; i < Accounts.size(); i++) {
            if (Accounts.get(i).getAcctNum().equals(accountNum)){
                if (Accounts.get(i).getBalance()>0){
                    throw new BalanceRemainingException(Accounts.get(i).
                            getBalance());
                }else {
                    Accounts.remove(i);//check else statement
                }
            }
        }

    }
    @Override
    public String toString() {
        String result = getName() + "\t" + getAddress();
        for (Company company:Companies) {
            result+= "\n" + "\t" + company.getName();
            for (BusinessAccount businessAccount:company.getBusinessAccounts()) {
                result+= "\n" + "\t" + "\t" + businessAccount.getAcctNum() + " " +
                        "" + businessAccount.getInterestRate() + "" +
                        " " + businessAccount.getBalance();
            }
        }
        for (Customer customer:Customers) {
            result+= "\n" + "\t" + customer.getName()+" "+customer.getSurname();
            for (PersonalAccount personalAccount:customer.getPersonalAccount()) {
                result+= "\n" + "\t" + "\t" + personalAccount.getAcctNum() + "" +
                        " " + personalAccount.getBalance();
            }
        }
        return result;
    }





}
class Account{
    private String accountNumber;
    private double balance;

    public Account() {
    }

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
        balance=0;
    }

    public Account(String accountNumber, double balance) throws
            InvalidAmountException{
        this.accountNumber = accountNumber;

        if (balance<0) {
            throw new InvalidAmountException(balance);
        }

        this.balance = balance;

    }

    public String getAcctNum() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) throws InvalidAmountException{
        if (amount<0){
            throw new InvalidAmountException(amount);
        }else {
            balance+=amount;
        }

    }

    public void withdrawal(double amount) throws InvalidAmountException{
        if (amount<0 || balance<amount){
            throw new InvalidAmountException(amount);
        }else {
            balance -= amount;
        }
    }

    @Override
    public String toString() {
        return "Account "+ getAcctNum()+" has "+ getBalance();
    }
}
class PersonalAccount extends Account{
    Random random=new Random();
    int number = random.nextInt(9999);


    private String name;
    private String surname;
    private String PIN;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    public PersonalAccount(String accountNumber, String name, String surname) {
        super(accountNumber);
        this.name = name;
        this.surname = surname;
        this.PIN = String.format("%04d",number);
    }

    public PersonalAccount(String accountNumber, double balance, String name,
                           String surname) {
        super(accountNumber, balance);
        this.name = name;
        this.surname = surname;
        this.PIN = String.format("%04d",number);
    }

    @Override
    public String toString() {
        return "Account "+getAcctNum()+" belonging to "+getName()+" "+
                getSurname().toUpperCase()+" has "+ getBalance();
    }
}
class BusinessAccount extends Account{
    private double interestRate;

    public BusinessAccount(String accountNumber, double interestRate) throws
            InvalidAmountException {
        super(accountNumber);
        if (interestRate<0){
            throw new InvalidAmountException(interestRate);
        }else {
            this.interestRate = interestRate;
        }
    }

    public BusinessAccount(String accountNumber, double balance,
                           double interestRate) throws InvalidAmountException{
        super(accountNumber, balance);
        if (interestRate<0){
            throw new InvalidAmountException(interestRate);
        }else{
        this.interestRate = interestRate;
        }
    }


    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) throws InvalidAmountException{
        if (interestRate<0){
            throw new InvalidAmountException(interestRate);
        }else {
            this.interestRate = interestRate;
        }
    }

    public double calculateInterest(){
        return getInterestRate()*getBalance();
    }
}
class Customer{
    private int id;
    private String name;
    private String surname;
    public ArrayList <PersonalAccount> personalAccount=new ArrayList<>();

    public ArrayList<PersonalAccount> getPersonalAccount() {
        return personalAccount;
    }

    public void setPersonalAccount(ArrayList<PersonalAccount> personalAccount) {
        this.personalAccount = personalAccount;
    }

    public Customer(int id, String name, String surname) throws
            InvalidAmountException{
        this.name = name;
        this.surname = surname;
        if (id<0){
            throw new InvalidAmountException(id);
        }else {
            this.id = id;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws InvalidAmountException{
        if (id<0){
            throw new InvalidAmountException(id);
        }else {
            this.id = id;
        }
    }

    public void openAccount(String acctNum){ //check
        personalAccount.add(new PersonalAccount(acctNum,0,getName(),
                getSurname()));


    }

    public PersonalAccount getAccount(String accountNum) throws
            AccountNotFoundException{
        for (int i = 0; i < personalAccount.size(); i++) {
            if (personalAccount.get(i).getAcctNum().equals(accountNum)){
                return personalAccount.get(i);
            }
        }
        throw new AccountNotFoundException(accountNum);

    }
    public void closeAccount(String accountNum)throws BalanceRemainingException,
            AccountNotFoundException {
        for (int i = 0; i < personalAccount.size(); i++) {
            if (personalAccount.get(i).getAcctNum().equals(accountNum)){
                if (personalAccount.get(i).getBalance()>0){
                    throw new BalanceRemainingException(personalAccount.get(i).
                            getBalance());
                }else {
                    personalAccount.remove(i);//check else statement
                }

            }
        }
            throw new AccountNotFoundException(accountNum);
    }

    @Override
    public String toString() {
        return getName()+" "+surname.toUpperCase();
    }
}
class Company{
    private int id;
    private String name;
    public ArrayList <BusinessAccount> businessAccounts=new ArrayList<>();

    public ArrayList<BusinessAccount> getBusinessAccounts() {
        return businessAccounts;
    }

    public void setBusinessAccounts(ArrayList<BusinessAccount> businessAccounts) {
        this.businessAccounts = businessAccounts;
    }

    public Company(int id, String name) throws InvalidAmountException{
        this.name = name;
        if (id<0){
            throw new InvalidAmountException(id);
        }else {
            this.id = id;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws InvalidAmountException{
        if (id<0){
            throw new InvalidAmountException(id);
        }else {
            this.id = id;
        }
    }

    public void openAccount(String acctNum, double rate){
        businessAccounts.add(new BusinessAccount(acctNum,0,rate));
    }

    public BusinessAccount getAccount(String acctNum) throws
            AccountNotFoundException{
        for (int i = 0; i < businessAccounts.size(); i++) {
            if (businessAccounts.get(i).getAcctNum().equals(acctNum)){
                return businessAccounts.get(i);
            }
        }
        throw new AccountNotFoundException(acctNum);
    }
    public void closeAccount(String accountNum) throws BalanceRemainingException,
            AccountNotFoundException{
        for (int i = 0; i < businessAccounts.size(); i++) {
            if (businessAccounts.get(i).getAcctNum().equals(accountNum)){
                if (businessAccounts.get(i).getBalance()>0){
                    throw new BalanceRemainingException(businessAccounts.get(i).
                            getBalance());
                }
                businessAccounts.remove(i);
            }
        }
        throw new AccountNotFoundException(accountNum);
    }

    public String toString(){
        return getName();
    }
}

class AccountNotFoundException extends RuntimeException{
    private String acctNum;

    public AccountNotFoundException(String acctNum) {
        this.acctNum = acctNum;
    }

    @Override
    public String toString() {
        return "AccountNotFoundException: "+ acctNum;
    }

}
class BalanceRemainingException extends RuntimeException{
    private double balance;

    public BalanceRemainingException(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "BalanceRemainingException: "+ balance;
    }

    public double getBalance() {
        return balance;
    }
}
class CompanyNotFoundException extends RuntimeException {
    private int id;
    private String name;

    public CompanyNotFoundException(int id) {
        this.id = id;
        name = null;
    }

    public CompanyNotFoundException(String name) {
        this.name = name;
        id = (int) (Math.random() * 100000);
    }

    @Override
    public String toString() {

        if (name != null) {
            return "CompanyNotFoundException: name- " + name;
        } else {
            return "CompanyNotFoundException: id - " + id;
        }

    }
}
class CustomerNotFoundException extends RuntimeException{
    private int id;
    private String name,surname;

    public CustomerNotFoundException(int id) {
        this.id = id;
        name=null;
        surname=null;
    }

    public CustomerNotFoundException(String name, String surname) {
        this.name = name;
        this.surname = surname;
        id=(int) (Math.random()*10000);
    }
    public String toString(){
        if (name !=null && surname !=null){
           return  "CustomerNotFoundException: name - "+name+ " "+surname;
        }else {
            return "CustomerNotFoundException: id -"+id;
        }

    }
}
class InvalidAmountException extends RuntimeException{
    private double amount;

    public InvalidAmountException(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "InvalidAmountException: " + amount;
    }
}

