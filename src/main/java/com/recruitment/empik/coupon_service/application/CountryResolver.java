package com.recruitment.empik.coupon_service.application;

import com.recruitment.empik.coupon_service.exception.CouponWriteErrorCode;
import com.recruitment.empik.coupon_service.exception.CouponWriteException;
import org.springframework.web.client.RestClient;

import java.util.Optional;

public class CountryResolver {

    private static final String IP_API_URL = "https://ipapi.co/{ip}/json/";

    public static String getCountryFromIP(String clientIp) {
        try {
            IpApiResponse response = RestClient.create().get()
                    .uri(IP_API_URL, clientIp)
                    .retrieve()
                    .body(IpApiResponse.class);

            return Optional.ofNullable(response)
                    .map(r -> r.country_code)
                    .orElse(null);

        } catch (Exception e) {
            throw new CouponWriteException(CouponWriteErrorCode.COUNTRY_INVALID);
        }
    }

    private record IpApiResponse(String country_code) {}
}
