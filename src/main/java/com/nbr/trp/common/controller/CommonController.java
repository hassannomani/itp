package com.nbr.trp.common.controller;

import com.nbr.trp.common.entity.*;
import com.nbr.trp.common.service.CommonService;
import com.nbr.trp.common.service.FileUploadService;
import com.nbr.trp.log.LoggerController;
import com.nbr.trp.user.entity.User;
import com.nbr.trp.user.service.UserDetailsImpl;
import com.nbr.trp.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.web.servlet.function.ServerResponse.ok;

@CrossOrigin(origins = "*", maxAge = 4800)
@RestController
@RequestMapping("/api/v1/common")
public class CommonController {

    @Autowired
    public CommonService commonService;

    @Autowired
    public FileUploadService fileUploadService;

    @Autowired
    LoggerController loggerController;

    @Autowired
    UserService userService;

    @Value("${spring.max-file-size}")
    private long max_file_size;


    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/division")
    public ResponseEntity<?> getDiv(HttpServletRequest request) {
        try{
            String ip = commonService.getIPAddress(request);
            List<Division> dv = commonService.getAllDivision();
            loggerController.ListGeneration("","All Division","",ip);
            // return ResponseEntity.ok(new MessageResponse("Representative registered successfully!"));
            return new ResponseEntity<>(dv, HttpStatus.CREATED);

        }catch(Exception e){
            loggerController.ErrorHandler(e);

            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/district")
    public ResponseEntity<?> getDistr(HttpServletRequest request) {
        try{
            String ip = commonService.getIPAddress(request);
            List<District> ds = commonService.getAllDistrict();
            loggerController.ListGeneration("","All Division","",ip);
            // return ResponseEntity.ok(new MessageResponse("Representative registered successfully!"));
            return new ResponseEntity<>(ds, HttpStatus.CREATED);

        }catch(Exception e){
            loggerController.ErrorHandler(e);

            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/thana")
    public ResponseEntity<?> getThana(HttpServletRequest request) {
        String ip = commonService.getIPAddress(request);

        try{

            List<Thana> ds = commonService.getAllThana();
            loggerController.ListGeneration("","All Thana","",ip);
            // return ResponseEntity.ok(new MessageResponse("Representative registered successfully!"));
            return new ResponseEntity<>(ds, HttpStatus.CREATED);

        }catch(Exception e){
            loggerController.ErrorHandler(e);

            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/citycorporation")
    public ResponseEntity<?> getCityCorporation(HttpServletRequest request) {
        String ip = commonService.getIPAddress(request);
        try{
            List<CityCorporation> ds = commonService.getAllCityCorporation();
            loggerController.ListGeneration("","All City Corporation","",ip);
            return new ResponseEntity<>(ds, HttpStatus.CREATED);

        }catch(Exception e){
            loggerController.ErrorHandler(e);

            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/file")
    public ResponseEntity<FileResponse> filepost(HttpServletRequest request, @RequestPart("file") MultipartFile file) throws FileSizeLimitExceededException {
        System.out.println("Exception happening 0");

        String ip = commonService.getIPAddress(request);
        long size = file.getSize();
        if(size>max_file_size){
            System.out.println("Exception happening");
            throw new FileSizeLimitExceededException("Exceeded",max_file_size,size);

        }
        try {
            File f = new ClassPathResource("").getFile();
            final Path path = Paths.get(f.getAbsolutePath() + File.separator + "static" + File.separator + "files");
            FileResponse fileResponse = fileUploadService.uploadFile(path, file, 1);
            loggerController.IncomingRequest(ip,"File Upload");
            return new ResponseEntity<>(fileResponse, HttpStatus.OK);

        }
        catch (FileSizeLimitExceededException e){
            System.out.println("FException----------------------");
            return new ResponseEntity<>(null,HttpStatus.PAYLOAD_TOO_LARGE);
        }
        catch (Exception e){
            System.out.println("Exception----------------------");
            return new ResponseEntity<>(null,HttpStatus.PAYLOAD_TOO_LARGE);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/file/{filename}")
    public ResponseEntity<Resource> load(HttpServletRequest request, @PathVariable String filename) {
        String ip = commonService.getIPAddress(request);
        try {
            Path root = Paths.get("target/classes/static/files");
            Resource resource = fileUploadService.retrieve(root, filename,1);
            loggerController.IncomingRequest(ip,"File Retrieval");

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.parseMediaType("application/pdf"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);


        } catch (Exception e) {
            loggerController.ErrorHandler(e);

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .contentType(MediaType.parseMediaType("application/pdf"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +"\"").body(null);

        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/photo")
    public ResponseEntity<FileResponse> photopost(HttpServletRequest request,@RequestPart("file") MultipartFile file) {
        String ip = commonService.getIPAddress(request);
        try {
            File f = new ClassPathResource("").getFile();
            final Path path = Paths.get(f.getAbsolutePath() + File.separator + "static" + File.separator + "photo");
            FileResponse fileResponse = fileUploadService.uploadFile(path, file, 0);
            loggerController.IncomingRequest(ip,"Photo Upload");
            return new ResponseEntity<>(fileResponse, HttpStatus.OK);

        } catch (IOException e) {
            loggerController.ErrorHandler(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value="/photo/{filename}",produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> loadPhoto(HttpServletRequest request,@PathVariable String filename) {
        String ip = commonService.getIPAddress(request);
        try {
            Path root = Paths.get("target/classes/static/photo");
            System.out.println(root);

            Resource resource = fileUploadService.retrieve(root, filename,0);
            loggerController.IncomingRequest(ip,"Photo Retrieval");

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentLength(resource.contentLength())
                    .body(resource);


        } catch (Exception e) {
            loggerController.ErrorHandler(e);
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(null);

        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/profilephoto")
    public ResponseEntity<FileResponse> profilephotopost(HttpServletRequest request,@RequestPart("file") MultipartFile file) {
        String ip = commonService.getIPAddress(request);
        UserDetailsImpl userDetails = commonService.getDetails();

        try {
            File f = new ClassPathResource("").getFile();
            final Path path = Paths.get(f.getAbsolutePath() + File.separator + "static" + File.separator + "profilephoto");
            FileResponse fileResponse = fileUploadService.uploadFile(path, file, 0);
            loggerController.IncomingRequest(ip,"Photo Upload");
            User u = userService.getUserByUsername(userDetails.getUsername()).orElse(null);
            if(u!=null){
                u.setPhoto(fileResponse.getFileUri());
                userService.saveUser(u);
                return new ResponseEntity<>(fileResponse, HttpStatus.OK);

            }else{
                return new ResponseEntity<>(fileResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (IOException e) {
            loggerController.ErrorHandler(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value="/get-profile-photo/{username}",produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> loadProfilePhoto(HttpServletRequest request,@PathVariable String username) {
        String ip = commonService.getIPAddress(request);
        try {
            User u = userService.getUserByUsername(username).orElse(null);
            String photo = u.getPhoto();
            System.out.println(photo);
            File theFile = new File(photo);
            String fname = theFile.getName();

            //String [] parts = photo.split("/");
            //System.out.println("Length is "+parts.length);
            Path root = Paths.get("target/classes/static/profilephoto/");
            //System.out.println(root);
            if(!fname.isEmpty()){
                Resource resource = fileUploadService.retrieve(root, fname,0);
                loggerController.IncomingRequest(ip,"Photo Retrieval");

                return ResponseEntity
                        .status(HttpStatus.OK)
                        .contentLength(resource.contentLength())
                        .body(resource);
            }else{
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(null);
            }



        } catch (Exception e) {
            loggerController.ErrorHandler(e);
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(null);

        }
    }
}
