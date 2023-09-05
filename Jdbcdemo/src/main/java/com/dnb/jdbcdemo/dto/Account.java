package com.dnb.jdbcdemo.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Account {
public String accountId;
public String accountHolderName;
public String accountType;
public float balance;
public String contactNumber;
public String address;
private LocalDate accountCreateDate = LocalDate.now();
private LocalDate dob;
private boolean accountStatus;

}
