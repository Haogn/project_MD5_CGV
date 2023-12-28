package com.ra.repository;

import com.ra.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoomRepository extends JpaRepository<Room, Long> {

   Boolean existsByTheater_Id(Long idTheater) ;
   Boolean existsByNameAndMovie_IdAndTimeSlot_Id(String nameRoom, Long idMovie, Long timeSlotId) ;
}
