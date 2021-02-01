package com.sleepseek.reservation.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationDTO {
    private Long id;

    private Long accommodationId;

    private Long stayId;

    private CustomerDTO customer;

    private String dateFrom;

    private String dateTo;

    private String createdAt;

    private String status;


    ;

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CustomerDTO {
        private String fullName;
        private String username;
        private String phoneNumber;
    }
}
