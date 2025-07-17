package com.example.bankmanagementsystem.Controller;

import com.example.bankmanagementsystem.Api.ApiResponse;
import com.example.bankmanagementsystem.Model.BankManagementSystemModel;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/v1/Bank-Management-System")
public class BankManagementSystemController {
    ArrayList<BankManagementSystemModel> customers = new ArrayList<>();
    static final AtomicLong idGenerator = new AtomicLong(1);

    //create customers
    @PostMapping("/add")
    public ApiResponse addCustomer(@RequestBody BankManagementSystemModel customer){
        customer.setId(idGenerator.getAndIncrement());
        customers.add(customer);
        return new ApiResponse("Successfully added Customer ");
    }

    //read customers
    @GetMapping("/get")
    public ArrayList<BankManagementSystemModel> getCustomers() {
        return customers;
    }

    //update customer
    @PutMapping("/update/{id}")
    public ApiResponse updateCustomer(@RequestBody BankManagementSystemModel customer,@PathVariable long id){

        for(BankManagementSystemModel c : customers) {
            if(c.getId()==id){
                c.setUserName(customer.getUserName());
                c.setBalance(customer.getBalance());
                return new ApiResponse("Successfully updated Customer");
            }
        }
        return new ApiResponse("Task not found!");
    }


    //delete customer
    @DeleteMapping("/delete/{id}")
    public ApiResponse deleteCustomer(@PathVariable long id){
        for(BankManagementSystemModel c : customers) {
            if(c.getId()==id){
                customers.remove(c);
                return new ApiResponse("Successfully deleted Customer");
            }
        }
        return new ApiResponse("Customer not found!");
    }

    //Deposit money
    @PutMapping("/deposit/{id}/{amount}")
    public Object deposit(@PathVariable long id, @PathVariable double amount){
        for(BankManagementSystemModel c : customers) {
            if(c.getId()==id){
                c.setBalance(c.getBalance()+amount);
                return c;
            }
        }
        return new ApiResponse("customer not found!");

    }
    //Withdraw money
    @PutMapping("/withdraw/{id}/{amount}")
    public Object withdraw(@PathVariable long id, @PathVariable double amount){
        for(BankManagementSystemModel c : customers) {
            if(c.getId()==id && c.getBalance()>=amount){
                c.setBalance(c.getBalance()-amount);
                return c;
            }else if(c.getId()==id){
                return new ApiResponse("money not enough");
            }
        }
        return new ApiResponse("customer not found!");

    }

    //transfer money to customer
    @PutMapping("/transfer/from/{sender}/to/{receiver}/{amount}")
    public Object transferMoney(@PathVariable String sender, @PathVariable String receiver, @PathVariable double amount) {
        BankManagementSystemModel senderCustomer = null;
        BankManagementSystemModel receiverCustomer = null;

        for (BankManagementSystemModel c : customers) {
            if (Objects.equals(c.getUserName(), sender)) {
                senderCustomer = c;
            } else if (Objects.equals(c.getUserName(), receiver)) {
                receiverCustomer = c;
            }
        }

        if (senderCustomer == null) {
            return new ApiResponse("Sender not found!");
        }

        if (receiverCustomer == null) {
            return new ApiResponse("Receiver not found!");
        }

        if (senderCustomer.getBalance() < amount) {
            return new ApiResponse("Insufficient balance!");
        }

        // Transfer money
        senderCustomer.setBalance(senderCustomer.getBalance() - amount);
        receiverCustomer.setBalance(receiverCustomer.getBalance() + amount);

        return new ApiResponse("Transfer successful from " + sender + " to " + receiver);
    }

}
