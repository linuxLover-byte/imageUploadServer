package com.example.imageUploadServer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            String uploadImage = imageService.uploadImage(file);
            return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error uploading image: " + e.getMessage());
        }
    }
    

    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
        byte[] imageData = imageService.downloadImage(fileName);
        if (imageData == null || imageData.length == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
        }
    
        // Determine the image type (e.g., from file extension or content type)
        String contentType = "image/png"; // default, you can enhance this logic
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(contentType))
                .body(imageData);
    }
    
}