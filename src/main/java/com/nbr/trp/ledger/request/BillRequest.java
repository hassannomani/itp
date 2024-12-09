package com.nbr.trp.ledger.request;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillRequest {
    String[] id;
    String role;
}
