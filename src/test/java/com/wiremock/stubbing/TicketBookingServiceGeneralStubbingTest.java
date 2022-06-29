package com.wiremock.stubbing;


import com.github.tomakehurst.wiremock.WireMockServer;
import com.wiremock.dto.CardDetails;
import com.wiremock.dto.PaymentUpdateResponse;
import com.wiremock.dto.TicketBookingPaymentRequest;
import com.wiremock.gateway.PaymentProcessorGateway;
import com.wiremock.service.TicketBookingService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class TicketBookingServiceGeneralStubbingTest {

    private TicketBookingService tbs;
    private WireMockServer wireMockServer;

    @BeforeEach
    void setup() {
        // configure wiremock server
        this.wireMockServer = new WireMockServer();
        configureFor("localhost", 8080);
        wireMockServer.start();

        PaymentProcessorGateway paymentProcessorGateway =
                new PaymentProcessorGateway("localhost", wireMockServer.port());
        tbs = new TicketBookingService(paymentProcessorGateway);
    }

    @Test
    void testCase1() {

        // GIVEN any operations, any url, return OK
        stubFor(any((anyUrl())).willReturn(ok()));
        // WHEN  define a request
        TicketBookingPaymentRequest ticketBookingPaymentRequest =
                new TicketBookingPaymentRequest("111", 200.0,
                        new CardDetails("111-111-111-111", LocalDate.now()));
        //  call the update
        PaymentUpdateResponse paymentUpdateResponse =
                tbs.updatePaymentDetails(ticketBookingPaymentRequest);

        // THEN
        Assertions.assertThat(paymentUpdateResponse.getStatus()).isEqualTo("SUCCESS");

    }

    @AfterEach
    void teardown() {
        this.wireMockServer.stop();
    }

}
