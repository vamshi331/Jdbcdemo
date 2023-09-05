package com.dnb.jdbcdemo;

import com.dnb.jdbcdemo.dto.Account;
import com.dnb.jdbcdemo.services.AccountServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class JDBCApplication {
    public static void main(String[] args) {
//        String a = "vams";
//        String b = null;
//        Optional<String> optional = Optional.ofNullable(b);
//        System.out.println(optional.isEmpty());

        Scanner sc = new Scanner(System.in);
        List<Account> accounts = new ArrayList<>();

        int choice = -1;
        do{
            System.out.println("Enter your choice : \n" +
                    "1. Create Account \n" +
                    "2. Search Account \n" +
                    "3. Exit");
            choice = sc.nextInt();

            if (choice < 1 || choice > 4) {
                System.out.println("Enter correct ");
                continue;
            }

            switch (choice){
                case 1 :
                    System.out.println("How many account you want to create : ");
                    int n = sc.nextInt();
                    for (int i = 0; i < n; i++) {
                        Account newAccount = new Account();
                        System.out.println("Account Id : ");
                        newAccount.setAccountId(sc.next());
                        System.out.println("Account Holder Name : ");
                        newAccount.setAccountHolderName(sc.next());
                        System.out.println("Account Type : ");
                        newAccount.setAccountType(sc.next());
                        System.out.println("Balance");
                        newAccount.setBalance(sc.nextFloat());
                        System.out.println("Contact Number : ");
                        newAccount.setContactNumber(sc.next());
                        System.out.println("Address : ");
                        newAccount.setAddress(sc.next());
                        System.out.println("DOB : ");
                        String date = sc.next();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate dob = LocalDate.parse(date, formatter);
                        newAccount.setDob(dob);
                        System.out.println("Account Status : ");
                        newAccount.setAccountStatus(true);

                        accounts.add(newAccount);
                    }

                    accounts.forEach(account -> {
                        AccountServiceImpl.getInstance().createAccount(account);
                    });

                    break;

                case 2:
                    System.out.println("Enter the account Id");
                    String accountId = sc.next();
                    Optional<Account> result = AccountServiceImpl.getInstance().getAccountById(accountId);

                    if (result.isPresent()) {
                        Account account = result.get();
                        System.out.println(account.getAccountId() + " " + account.getAccountHolderName() + " " + account.getAccountType() + " " +
                                account.getBalance() + " " + account.getAccountCreateDate() + " " + account.getDob() + " " + account.isAccountStatus());
                    }else{
                        System.out.println("No account found for this " + accountId + " account id.");
                        System.out.println("Good");
                        System.out.println("Good1");
                    }
                    break;

                case 3 :
                    break;
            }
        }while(choice != 3);
    }

}
