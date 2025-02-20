package com.example.imageUploadServer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
// import java.awt.Image; // Removed to avoid conflict

import org.hibernate.annotations.Type;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;

    @Lob
    private byte[] picByte;

    public byte[] getImageData() {
        return picByte;
    }

   public static byte[] compressImage(byte[] data) throws IOException {
    // Create a buffered image from the byte array
    InputStream in = new ByteArrayInputStream(data);
    BufferedImage originalImage = ImageIO.read(in);

    // Set target width and height (e.g., 800px width)
    int targetWidth = 800;
    int targetHeight = (originalImage.getHeight() * targetWidth) / originalImage.getWidth();

    // Create a new image with the target dimensions
    java.awt.Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, java.awt.Image.SCALE_SMOOTH);
    BufferedImage compressedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);

    Graphics2D g = compressedImage.createGraphics();
    g.drawImage(scaledImage, 0, 0, null);
    g.dispose();

    // Convert the compressed image to byte array
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ImageIO.write(compressedImage, "jpg", outputStream); // Save as JPEG
    return outputStream.toByteArray();
}
}