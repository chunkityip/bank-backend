package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * CK: Create CustomerDTO to get id , name email from datebase : bank
 */

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class CustomerDTO {
//    private Long id;
//    private String name;
//    private String email;
//    private List<String> accounts;  // Changed from Long to String for consistency
//}

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private Long id;
    private String name;
    private List<Long> accounts;  // Changed from Long to String for consistency
}
