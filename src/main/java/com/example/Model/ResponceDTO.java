package com.example.Model;


import lombok.Data;

import java.math.BigDecimal;

//    {
//            "rqUID": "58dgtf565j8547f64ke7",
//            "clientId": "3050000000000000000",
//            "account": "30500000000000000001",
//            "currency": "RU",
//            "balance": "16000.00",
//            "maxLimit": "50000.00"
//    }

@Data
public class ResponceDTO {

    private String rqUID;
    private String clientId;
    private String account;
    private String currency;
    private BigDecimal balance;
    private BigDecimal maxLimit;

}
