package com.nbr.trp.test.entity;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class testEntity {
    String timestamp;

    String version;

    String logger;

    String event_type;

    String thread;

    String message;
}
