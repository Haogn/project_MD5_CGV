package com.ra.repository;

import com.ra.entity.BookingDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookingDetailRepository extends JpaRepository<BookingDetail, Long> {
//            (Long idUser,Long idMovie, Long idTheater, Long idRoom, Long idTimeSlot, Long idChair);
    Page<BookingDetail> findByStatus(Boolean status, Pageable pageable) ;
}
