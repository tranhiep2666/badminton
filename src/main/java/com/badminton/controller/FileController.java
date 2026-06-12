package com.badminton.controller;

import com.badminton.dto.response.ResponseDTO;
import com.badminton.dto.response.UploadResponse;
import com.badminton.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(

            @RequestParam("file")
            MultipartFile file
    ) {

        String imageUrl =
                fileService.uploadFile(file);

        return ResponseEntity.ok(

                ResponseDTO.builder()

                        .success(true)

                        .message(
                                "Upload successful"
                        )

                        .data(
                                UploadResponse.builder()
                                        .imageUrl(imageUrl)
                                        .build()
                        )

                        .build()
        );
    }
}