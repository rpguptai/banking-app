package com.bank.account;

import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bank.account.model.Transaction;

public class MyUuidApp {
public static void main(String[] args) {
    UUID uuid = UUID.randomUUID();
    String uuidAsString = uuid.toString();

    System.out.println("Your UUID is: " + uuidAsString);
    System.out.println(Transaction.TransactionType.TRANSFER);
    System.out.println(new BCryptPasswordEncoder().encode("password"));
}
}