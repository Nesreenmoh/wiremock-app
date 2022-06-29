package com.wiremock.stubbing;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.wiremock.dto.CardDetails;
import com.wiremock.dto.TicketBookingPaymentRequest;
import com.wiremock.dto.TicketBookingResponse;
import com.wiremock.gateway.PaymentProcessorGateway;
import com.wiremock.service.TicketBookingService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class TicketBookingServiceRequestStubbingTest {
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
    void testCase2() {

        // GIVEN any operations, any url, return OK
        stubFor(post(urlPathEqualTo("/payments"))
                .withRequestBody(equalToJson("{\n" +
                        "    \"cardNumber\": \"111-111-111-111\",\n" +
                        "    \"cardExpiryDate\": \"2022-06-29\",\n" +
                        "    \"amount\": 200.0\n" +
                        "}"))
                .willReturn(okJson("{\n" +
                        "    \"paymentId\": \"3333\",\n" +
                        "    \"paymentResponseStatus\": \"SUCCESS\"\n" +
                        "}")));
        // WHEN  define a request
        TicketBookingPaymentRequest ticketBookingPaymentRequest =
                new TicketBookingPaymentRequest("111", 200.0,
                        new CardDetails("111-111-111-111", LocalDate.of(2022,06,29)));
        //  call the update

        TicketBookingResponse ticketBookingResponse =
                tbs.payForBooking(ticketBookingPaymentRequest);

        // THEN
        Assertions.assertThat(ticketBookingResponse)
                .isEqualTo(new TicketBookingResponse("111", "3333", TicketBookingResponse.BookingResponseStatus.SUCCESS));

    }

    @AfterEach
    void teardown() {
        this.wireMockServer.stop();
    }
}
