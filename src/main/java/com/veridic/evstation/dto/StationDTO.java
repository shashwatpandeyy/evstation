package com.veridic.evstation.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationDTO {

    Long id;
    String name;
    byte[] image;
    Integer pricing;
    String address;
}
