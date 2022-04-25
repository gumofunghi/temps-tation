package com.gumo.temps.service;

import com.gumo.temps.model.UploadedFile;
import com.gumo.temps.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    private FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void save(MultipartFile file) throws IOException {
        UploadedFile uploadedFile = new UploadedFile();
        uploadedFile.setName(StringUtils.cleanPath(file.getOriginalFilename()));
        uploadedFile.setContentType(file.getContentType());
        uploadedFile.setData(file.getBytes());
        uploadedFile.setSize(file.getSize());
        fileRepository.save(uploadedFile);
    }

    public Optional<UploadedFile> getFile(String id) {
        return fileRepository.findById(id);
    }

    public List<UploadedFile> getAllFiles(){
        return fileRepository.findAll();
    }

}
