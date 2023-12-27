package com.ra.repository;

import com.ra.entity.BookingDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookingDetailRepository extends JpaRepository<BookingDetail, Long> {
    Boolean existsByUsersIdAndChairRoomMovieIdAndChairRoomTheaterLocationIdAndChairRoomTheaterIdAndChairRoomIdAndChairTimeSlotIdAndChairId(Long userId, Long movieId, Long locationId, Long theaterId, Long roomId, Long timeSlotId, Long chairId);
    Page<BookingDetail> findByStatus(Boolean status, Pageable pageable) ;
}
