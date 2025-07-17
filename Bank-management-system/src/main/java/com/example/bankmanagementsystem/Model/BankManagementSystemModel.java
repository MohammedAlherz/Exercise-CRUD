package com.example.bankmanagementsystem.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankManagementSystemModel {
    private long id;
    private String userName;
    private double balance;

    public BankManagementSystemModel(String userName) {
        this.userName = userName;
    }
}
