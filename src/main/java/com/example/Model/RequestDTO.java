package com.example.Model;

import lombok.*;
// {
//   "rqUID": "58dgtf565j8547f64ke7",
//   "clientId": "3050000000000000000",
//   "account": "30500000000000000001",
//   "openDate": "2020-01-01",
//   "closeDate": "2025-01-01"
// }


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestDTO {

    private String rqUID;
    private String clientId;
    private String account;
    private String openDate;
    private String closeDate;

}
