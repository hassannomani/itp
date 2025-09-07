package com.nbr.trp.common.service;

import com.nbr.trp.common.entity.FileResponse;
import liquibase.util.FilenameUtil;
import liquibase.util.file.FilenameUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.Map;

@Service
public class FileUploadServiceImpl implements FileUploadService{

    @Override
    public FileResponse uploadFile(Path p, MultipartFile file, int flag, Long val) {

        try {

            final Path path = p;

            if (!Files.exists(path)) {
                System.out.println("doesnt exist");
                try {
                    Files.createDirectories(path);
                } catch (IOException e) {
                    throw new RuntimeException("Could not initialize folder for upload!");
                }
            } else{
                System.out.println("exist");

            }

//            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//            Long val = timestamp.getTime();
            String pathFinal = "";
            if(flag==1)
                pathFinal = path + File.separator + val + ".pdf";
            else {
                pathFinal = path + File.separator + val + ".jpg";
            }
            Path saveTO = Paths.get(pathFinal);

            Path filePath = path.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), saveTO, StandardCopyOption.REPLACE_EXISTING);

            FileResponse fileResponse = new FileResponse();
            fileResponse.setFileUri(saveTO.toString());
            fileResponse.setFilename(file.getOriginalFilename());

            return fileResponse;


        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Resource retrieve(Path p, String filename, int flag) {
        System.out.println(p);
        System.out.println(filename);
        try{
            Path root = p;
            Path file = root.resolve(filename);

            System.out.println(file);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");

            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
