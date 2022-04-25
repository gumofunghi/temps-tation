package com.gumo.temps.repository;

import com.gumo.temps.model.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<UploadedFile, String> {

}
