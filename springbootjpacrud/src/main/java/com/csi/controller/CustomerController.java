package com.csi.controller;

import com.csi.exception.RecordNotFoundException;
import com.csi.model.Customer;
import com.csi.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    CustomerServiceImpl customerServiceImpl;

    @PostMapping("/signup")
    public ResponseEntity<Customer> signUp(@Valid @RequestBody Customer customer) {
        return new ResponseEntity<>(customerServiceImpl.signUp(customer), HttpStatus.CREATED);
    }

    @GetMapping("/signIn/{custEmailId}/{custPassword}")
    public ResponseEntity<Boolean> signIn(@PathVariable String custEmailId, @PathVariable String custPassword) {
        return ResponseEntity.ok(customerServiceImpl.signIn(custEmailId, custPassword));
    }

    @GetMapping("/getdatabyid")
    public ResponseEntity<Optional<Customer>> getDataById(@RequestParam int custId) {
        return ResponseEntity.ok(customerServiceImpl.getDataById(custId));
    }

    @GetMapping("/getdatabyname")
    public ResponseEntity<List<Customer>> getDataByName(@RequestParam String custName) {
        return ResponseEntity.ok(customerServiceImpl.getDataByName(custName));
    }

    @GetMapping("/getalldata")
    public ResponseEntity<List<Customer>> getAllData() {
        return ResponseEntity.ok(customerServiceImpl.getAllData());
    }

    @PutMapping("/updatedata/{custId}")
    public ResponseEntity<Customer> updateData(@RequestParam int custId, @Valid @RequestBody Customer customer) {
        Customer customer1 = customerServiceImpl.getDataById(custId).orElseThrow(() -> new RecordNotFoundException("Customer ID Does Not Exist"));

        customer1.setCustDOB(customer.getCustDOB());
        customer1.setCustName(customer.getCustName());
        customer1.setCustContactNumber(customer.getCustContactNumber());
        customer1.setCustAddress(customer.getCustAddress());
        customer1.setCustPassword(customer.getCustPassword());
        customer1.setCustAccountBalance(customer.getCustAccountBalance());
        customer1.setCustEmailId(customer.getCustEmailId());

        return new ResponseEntity<>(customerServiceImpl.updateData(customer1), HttpStatus.CREATED);
    }

    @DeleteMapping("/deletedatabyid/{custId}")
    public ResponseEntity<String> deleteDataById(@PathVariable int custId) {
        customerServiceImpl.deleteDataById(custId);
        return ResponseEntity.ok("Data Deleted Successfully");
    }

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello(){

        return ResponseEntity.ok("Welcome to Fintech csi");
    }
}
