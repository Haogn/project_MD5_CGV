package com.ra.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "booking_detail")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean status ;
    private Double totalAmount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date bookingDate ;
    @ManyToOne
    @JoinColumn(name = "chair_id")
    private Chair chair ;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
}
