package com.veridic.evstation.repository;

import com.veridic.evstation.entity.Station;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StationRepository extends JpaRepository<Station, Long> {

    @Query("select station from Station station")
    Page<Station> getStations(Pageable pageable);
}
