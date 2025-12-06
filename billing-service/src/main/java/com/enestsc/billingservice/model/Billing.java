package com.enestsc.billingservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity()
@Table(name = "billings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID accountId;

    @NotBlank(message = "Status cannot be blank")
    private String status;

    @NotNull(message = "Fee amount cannot be null")
    private double feeAmount;

    @NotNull(message = "Patient ID cannot be null")
    private UUID patientId;

}
