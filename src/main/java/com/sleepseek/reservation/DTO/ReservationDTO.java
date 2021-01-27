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

    private CustomerDTO customer;

    private String dateFrom;

    private String dateTo;

    private String createdAt;

    private Boolean confirmed;

    private Boolean completed;

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CustomerDTO {
        private String username;
        private String phoneNumber;
    }
}
