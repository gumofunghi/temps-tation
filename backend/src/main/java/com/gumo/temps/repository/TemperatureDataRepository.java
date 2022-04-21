package com.gumo.temps.repository;

import com.gumo.temps.model.TemperatureData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemperatureDataRepository extends JpaRepository<TemperatureData, Long> {
    //use JpaRepository's default methods
}
