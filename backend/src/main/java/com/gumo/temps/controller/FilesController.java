package com.gumo.temps.controller;

import com.gumo.temps.model.FileResponse;
import com.gumo.temps.model.UploadedFile;
import com.gumo.temps.response.MessageResponse;
import com.gumo.temps.service.FileService;
import org.apache.catalina.webresources.FileResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("files")
public class FilesController {

    private FileService fileService;

    @Autowired
    public FilesController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file")MultipartFile file) {
        try{
            fileService.save(file);
            return ResponseEntity.ok(new MessageResponse("File uploaded successfully: " + file.getOriginalFilename()));
        } catch (IOException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Could not upload the file: " + file.getOriginalFilename()));
        }
    }

    @GetMapping("/all")
    public List<FileResponse> list(){
        return fileService.getAllFiles()
                          .stream()
                          .map(this::mapFileResponse)
                          .collect(Collectors.toList());
    }

    private FileResponse mapFileResponse(UploadedFile uploadedFile) {
        String uriDownload = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/")
                .path(Long.toString(uploadedFile.getId()))
                .toUriString();

        FileResponse fileResponse = new FileResponse();
        fileResponse.setId(uploadedFile.getId());
    }



}
