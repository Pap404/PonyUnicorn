package com.petshop.enterpraise.service;

import com.petshop.enterpraise.config.UnicornStorageEx;
import com.petshop.enterpraise.properties.PonyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service

public class UnicornService {
    private final Path fileStorageLocation;

    @Autowired
    public UnicornService(PonyProperties unicornProperties) {
        this.fileStorageLocation = Paths.get(unicornProperties.getUploadDir())
                .toAbsolutePath()
                .normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new UnicornStorageEx("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(fileName.contains("..")) {
                throw new UnicornStorageEx("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Path unicornTargetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(),unicornTargetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new UnicornStorageEx("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
}
