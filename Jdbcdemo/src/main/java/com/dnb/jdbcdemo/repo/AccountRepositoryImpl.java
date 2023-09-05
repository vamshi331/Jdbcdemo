package com.dnb.jdbcdemo.repo;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

import com.dnb.jdbcdemo.dto.Account;
import com.dnb.jdbcdemo.utils.DBUtils;

public class AccountRepositoryImpl implements AccountRepository{

    private static AccountRepository accountRepository;
    private AccountRepositoryImpl(){

    }

    public static AccountRepository getInstance(){
        synchronized (AccountRepositoryImpl.class){
            if (accountRepository == null)
                accountRepository = new AccountRepositoryImpl();
        }

        return accountRepository;
    }
    
    @Override
    public Account createAccount(Account account) {
        Optional<Connection> connection = DBUtils.getConnection();
        String createAccountStatement = "insert into account "
                + "(accountId, accountHolderName, accountType, balance, contactNumber, address, accountCreatedDate, dob, accountStatus) "
                + "values (?,?,?,?,?,?,?,?,?)";

        PreparedStatement preparedStatement = null;
        if (connection.isPresent()) {
            try {

                preparedStatement = connection.get().prepareStatement(createAccountStatement);
                preparedStatement.setString(1, account.getAccountId());
                preparedStatement.setString(2, account.getAccountHolderName());
                preparedStatement.setString(3, account.getAccountType());
                preparedStatement.setFloat(4, account.getBalance());
                preparedStatement.setString(5, account.getContactNumber());
                preparedStatement.setString(6, account.getAddress());
                preparedStatement.setDate(7, Date.valueOf(account.getAccountCreateDate()));
                preparedStatement.setDate(8, Date.valueOf(account.getDob()));
                preparedStatement.setBoolean(9, account.isAccountStatus());
                int result = preparedStatement.executeUpdate();

                if (result > 0)
                    return account;
        } catch(SQLException e){
            e.printStackTrace();
        }finally{
            if (connection.isPresent()) {
                DBUtils.closeConnection(connection.get());
            }
        }
    }

        return null;
    }

    @Override
    public Optional<Account> getAccountById(String accountId) {
        Optional<Connection> connection = DBUtils.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from account where accountId = ?";

        if (connection.isPresent()) {
            try {
                preparedStatement = connection.get().prepareStatement(query);
                preparedStatement.setString(1, accountId);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()){
                    Account account = new Account();
                    account.setAccountId(resultSet.getString("accountId"));
                    account.setAccountHolderName(resultSet.getString("accountHolderName"));
                    account.setAccountType(resultSet.getString("accountType"));
                    account.setBalance(resultSet.getFloat("balance"));
                    account.setContactNumber(resultSet.getString("contactNumber"));
                    account.setAddress(resultSet.getString("address"));
                    account.setAccountCreateDate(resultSet.getDate("accountCreatedDate").toLocalDate());
                    account.setDob(resultSet.getDate("dob").toLocalDate());
                    account.setAccountStatus(resultSet.getBoolean("accountStatus"));

                    return Optional.of(account);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

}