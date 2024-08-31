package com.example.demo.controller;

import com.example.demo.enums.BankType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sortCode")
@CrossOrigin
public class BankInfoController {

    @GetMapping
    public ResponseEntity<String> getSortCode() {
        String sortCode = BankType.DEFAULT.getSortCode();
        return ResponseEntity.ok(sortCode);
    }
}