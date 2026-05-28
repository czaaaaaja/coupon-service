package com.recruitment.empik.coupon_service.api;

import com.recruitment.empik.coupon_service.api.controller.CouponController;
import com.recruitment.empik.coupon_service.application.CouponCreator;
import com.recruitment.empik.coupon_service.application.CouponResolver;
import com.recruitment.empik.coupon_service.domain.model.Coupon;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CouponController.class)
public class CouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CouponCreator couponCreator;

    @Autowired
    @MockitoBean
    private CouponResolver couponResolver;


    @Test
    public void create_shouldReturnCreatedCoupon() throws Exception {

        LocalDate creationDate = LocalDate.now();
        Coupon coupon = createCoupon(creationDate);

        when(couponCreator.createCoupon(any())).thenReturn(coupon);

        String requestPayload = """
                {
                    "maxUsages": 10,
                    "code": "CODE",
                    "country": "PL"
                }
                """;

        mockMvc.perform(post("/api/coupons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("CODE"))
                .andExpect(jsonPath("$.maxUsages").value(10))
                .andExpect(jsonPath("$.usageCount").value(0))
                .andExpect(jsonPath("$.country").value("PL"))
                .andExpect(jsonPath("$.creationDate").value(creationDate.toString()));
    }

    @Test
    public void create_shouldReturnBadRequestWithIncorrectMaxUsages() throws Exception {
        String requestPayload = """
                {
                    "maxUsages": -10,
                    "code": "CODE",
                    "country": "PL"
                }
                """;
        //controller level validation, no "when" stub needed

        mockMvc.perform(post("/api/coupons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestPayload))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void get_shouldReturnCoupon() throws Exception {
        LocalDate creationDate = LocalDate.now();
        Coupon coupon = createCoupon(creationDate);

        when(couponResolver.getCoupon("CODE")).thenReturn(coupon);
        mockMvc.perform(get("/api/coupons/CODE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("CODE"))
                .andExpect(jsonPath("$.maxUsages").value(10))
                .andExpect(jsonPath("$.usageCount").value(0))
                .andExpect(jsonPath("$.country").value("PL"))
                .andExpect(jsonPath("$.creationDate").value(creationDate.toString()));
    }

    @Test
    public void use_shouldReturnCoupon() throws Exception {
        LocalDate creationDate = LocalDate.now();
        Coupon coupon = createCoupon(creationDate);

        String requestPayload = """
                {
                    "userId": "USER",
                    "clientIp": "31.0.0.1"
                }
                """;

        when(couponResolver.useCoupon(any(), any())).thenReturn(coupon);

        mockMvc.perform(post("/api/coupons/CODE")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("CODE"))
                .andExpect(jsonPath("$.maxUsages").value(10))
                .andExpect(jsonPath("$.usageCount").value(0))
                .andExpect(jsonPath("$.country").value("PL"))
                .andExpect(jsonPath("$.creationDate").value(creationDate.toString()));
    }

    private Coupon createCoupon(LocalDate creationDate) {
        Coupon coupon = new Coupon();
        coupon.setCode("CODE");
        coupon.setCreationDate(creationDate);
        coupon.setCountry("PL");
        coupon.setMaxUsages(10);
        return coupon;
    }
}
