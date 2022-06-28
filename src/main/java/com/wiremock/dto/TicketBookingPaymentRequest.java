package com.wiremock.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TicketBookingPaymentRequest {
    private  String bookingId;
    private  Double amount;
    private  CardDetails cardDetails;
    private boolean fraudAlert = false;

    public TicketBookingPaymentRequest(String bookingId, Double amount, CardDetails cardDetails) {
        this.bookingId = bookingId;
        this.amount = amount;
        this.cardDetails = cardDetails;
    }
}
