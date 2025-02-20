package com.example.imageUploadServer;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ImageService {

    public String uploadImage(MultipartFile file) throws IOException {
        // Check file size first (you can also validate file extension or type)
        if (file.getSize() > 10 * 1024 * 1024) { // 10MB size limit
            throw new IOException("File size exceeds the maximum allowed size (10MB)");
        }

        // Compress the image (example for PNG/JPEG)
        byte[] compressedImage = compressImage(file.getBytes());

        // Save the image (use your logic for saving to the database or filesystem)
        // For now, we just return a success message
        return "Image uploaded successfully!";
    }

    private byte[] compressImage(byte[] data) throws IOException {
        // Create a buffered image from the byte array
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(data));

        // Set the target width and height (e.g., resize to 800px width)
        int targetWidth = 800;
        int targetHeight = (originalImage.getHeight() * targetWidth) / originalImage.getWidth();

        // Resize the image
        Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage compressedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = compressedImage.createGraphics();
        g.drawImage(scaledImage, 0, 0, null);
        g.dispose();

        // Convert the compressed image back to a byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(compressedImage, "png", outputStream); // Save as PNG or JPG
        return outputStream.toByteArray();
    }

    public byte[] downloadImage(String fileName) {
        // Implement logic to retrieve the image from the database or filesystem
        return new byte[0]; // Placeholder
    }
}