package com.gumo.temps.repository;

import com.gumo.temps.model.UploadedData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadedDataRepository extends JpaRepository<UploadedData, Long> {
    //use JpaRepository's default methods
}
