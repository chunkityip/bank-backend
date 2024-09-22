package com.example.demo.controller;

import com.example.demo.enums.BankType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sortCode")
//@CrossOrigin(origins = "http://localhost:4200") // change it if you like to use our team ui
@CrossOrigin // change it if you like to use mallon-bank-ui
public class BankInfoController {

    @GetMapping
    public ResponseEntity<String> getSortCode() {
        String sortCode = BankType.DEFAULT.getSortCode();
        return ResponseEntity.ok(sortCode);
    }
}