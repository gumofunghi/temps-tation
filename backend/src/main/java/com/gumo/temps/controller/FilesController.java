package com.gumo.temps.controller;

import com.gumo.temps.model.FileResponse;
import com.gumo.temps.model.UploadedFile;
import com.gumo.temps.response.MessageResponse;
import com.gumo.temps.security.service.UserDetailsServiceImplementation;
import com.gumo.temps.service.FileService;
import org.apache.catalina.webresources.FileResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("files/")
public class FilesController {

    private FileService fileService;
    private UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @Autowired
    public FilesController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file")MultipartFile file) {
        try{
            //upload

        } catch (IOException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Could not upload the file: " + file.getOriginalFilename()));
        }
    }

    @GetMapping("all")
    public List<FileResponse> list(){
        return fileService.getAllFiles()
                          .stream()
                          .map(this::mapFileResponse)
                          .collect(Collectors.toList());
    }

    private FileResponse mapFileResponse(UploadedFile uploadedFile) {
        String uriDownload = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/")
                .path(uploadedFile.getId())
                .toUriString();

        FileResponse fileResponse = new FileResponse();
        fileResponse.setId(uploadedFile.getId());
        fileResponse.setName(uploadedFile.getName());
        fileResponse.setContentType(uploadedFile.getContentType());
        fileResponse.setSize(uploadedFile.getSize());
        fileResponse.setUrl(uriDownload);

        return fileResponse;

    }

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        Optional<UploadedFile> uploadedFileOptional = fileService.getFile(id);
        if (!uploadedFileOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        UploadedFile uploadedFile = uploadedFileOptional.get();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + uploadedFile.getName() + "\"")
                .contentType(MediaType.valueOf(uploadedFile.getContentType()))
                .body(uploadedFile.getData());
    }



}
