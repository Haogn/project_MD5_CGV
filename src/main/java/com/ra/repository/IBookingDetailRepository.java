package com.ra.repository;

import com.ra.entity.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookingDetailRepository extends JpaRepository<BookingDetail, Long> {
}
