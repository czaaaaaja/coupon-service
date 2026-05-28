package com.recruitment.empik.coupon_service.api;

import com.recruitment.empik.coupon_service.api.request.CouponCreationRequest;
import com.recruitment.empik.coupon_service.api.request.CouponUseRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class ApiIntegrationTest {

    @LocalServerPort
    private int port;

    private WebTestClient testClient;

    @BeforeEach
    void setUp() {
        this.testClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port + "/api")
                .build();
    }

    @Test
    public void createAndGetCoupon_shouldReturnUpdatedCoupon() {

        //creating a new coupon
        CouponCreationRequest couponCreationRequest = new CouponCreationRequest(10, "CODE1", "PL");

        testClient.post()
                .uri("/coupons")
                .bodyValue(couponCreationRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.code").isEqualTo("CODE1")
                .jsonPath("$.maxUsages").isEqualTo(10)
                .jsonPath("$.usageCount").isEqualTo(0)
                .jsonPath("$.country").isEqualTo("PL");

        //using the coupon
        CouponUseRequest couponUseRequest = new CouponUseRequest("SOME_USER","31.0.0.1");

        testClient.post()
                .uri("/coupons/CODE1")
                .bodyValue(couponUseRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.code").isEqualTo("CODE1")
                .jsonPath("$.maxUsages").isEqualTo(10)
                .jsonPath("$.usageCount").isEqualTo(1)
                .jsonPath("$.country").isEqualTo("PL");
    }

    @Test
    public void getNonExistingCoupon_shouldReturn404() {
        testClient.get()
                .uri("/coupons/NON_EXISTING_CODE")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void addDuplicateCoupon_shouldReturn400() {
        CouponCreationRequest couponCreationRequest = new CouponCreationRequest(10, "CODE", "PL");

        testClient.post()
                .uri("/coupons")
                .bodyValue(couponCreationRequest)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void useCouponFromAnotherCountry_shouldReturn400() {

        CouponCreationRequest couponCreationRequest = new CouponCreationRequest(10, "CODE2", "PL");

        testClient.post()
                .uri("/coupons")
                .bodyValue(couponCreationRequest)
                .exchange()
                .expectStatus().isCreated();

        CouponUseRequest couponUseRequest = new CouponUseRequest("SOME_USER","86.249.12.34"); //some French IP

        testClient.post()
                .uri("/coupons/CODE2")
                .bodyValue(couponUseRequest)
                .exchange()
                .expectStatus().isBadRequest();

    }

}
