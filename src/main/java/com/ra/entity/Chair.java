package com.ra.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chari")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Chair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String name ;
    private Boolean active ;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room ;

    @OneToOne(mappedBy = "chair", cascade = CascadeType.ALL)
    private TimeSlot timeSlot;
}
