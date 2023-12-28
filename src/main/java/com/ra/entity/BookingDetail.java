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
    private Double discount ;
    private Double subTotal ;
    private Double totalAmount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date bookingDate ;
    @ManyToOne
    @JoinColumn(name = "chair_id")
    private Chair chair ;
//    private Long movieId ;
//    private Long roomId ;
//    private Long timeSlotId;
//    private Long theaterId ;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
}
