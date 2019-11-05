package com.petshop.enterpraise.service;

import com.petshop.enterpraise.config.ElfStorageEx;
import com.petshop.enterpraise.properties.PonyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service

public class ElfService {

    private final Path fileStorageLocation;

    @Autowired
    public ElfService(PonyProperties elfProperties) {
        this.fileStorageLocation = Paths.get(elfProperties.getUploadDir())
                .toAbsolutePath()
                .normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new ElfStorageEx("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(FilePart file) {
        String fileName = StringUtils.cleanPath(file.filename());

        try {
            if(fileName.contains("..")) {
                throw new ElfStorageEx("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Path elfTargetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy((Path) file.content(), elfTargetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new ElfStorageEx("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
}
