package com.wiremock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class PaymentProcessorResponseRequest {

    private final String cardNumber;
    private final LocalDate cardExpiryDate;
    private final Double amount;
}
