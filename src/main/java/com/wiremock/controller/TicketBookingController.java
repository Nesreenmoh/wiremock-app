package com.wiremock.controller;

import com.wiremock.dto.PaymentUpdateResponse;
import com.wiremock.dto.TicketBookingPaymentRequest;
import com.wiremock.dto.TicketBookingResponse;
import com.wiremock.service.TicketBookingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TicketBookingController {
    private TicketBookingService ticketBookingService;

    public TicketBookingController(TicketBookingService ticketBookingService) {
        this.ticketBookingService = ticketBookingService;
    }

    @PostMapping("/bookTicket")
    TicketBookingResponse payForTicket(@RequestBody TicketBookingPaymentRequest ticketBookingPaymentRequest){
        return ticketBookingService.payForBooking(ticketBookingPaymentRequest);
    }

    @PostMapping("/updatePayment")
    PaymentUpdateResponse updatePaymentDetails(@RequestBody TicketBookingPaymentRequest ticketBookingPaymentRequest){
        return ticketBookingService.updatePaymentDetails(ticketBookingPaymentRequest);
    }

    @PostMapping("/batchPayment")
    List<TicketBookingResponse> batchPayment(List<TicketBookingPaymentRequest> bookingPayment){
        return ticketBookingService.batchPayment(bookingPayment);
    }
}
