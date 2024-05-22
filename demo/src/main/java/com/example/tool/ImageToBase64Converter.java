package com.example.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageToBase64Converter {

    public static String convertImageToBase64(String imagePath) {
        File file = new File(imagePath);
        try (FileInputStream imageInFile = new FileInputStream(file)) {
            byte[] imageData = new byte[(int) file.length()];
            imageInFile.read(imageData);
            return Base64.getEncoder().encodeToString(imageData);
        } catch (IOException e) {
            System.out.println("Error while reading the image: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        String imagePath = "path_to_your_image.png"; // 设置本地图片路径
        String base64Image = convertImageToBase64(imagePath);
        if (base64Image != null) {
            System.out.println("Base64 Encoded Image String:\n" + base64Image);
        } else {
            System.out.println("Failed to convert image to Base64.");
        }
    }
}
