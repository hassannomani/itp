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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;

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
    @GetMapping("/taxesbarassoc")
    public ResponseEntity<?> getTaxesBarAssoc(HttpServletRequest request) {
        String ip = commonService.getIPAddress(request);
        try{
            List<TaxesBarAssociation> ds = commonService.getAllTaxesBar();
            loggerController.ListGeneration("","All Taxes Bar","",ip);
            return new ResponseEntity<>(ds, HttpStatus.CREATED);

        }catch(Exception e){
            loggerController.ErrorHandler(e);

            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/file")
    public ResponseEntity<FileResponse> filepost(HttpServletRequest request, @RequestPart("file") MultipartFile file) throws FileSizeLimitExceededException {

        String ip = commonService.getIPAddress(request);
        long size = file.getSize();
        if(size>max_file_size){
            System.out.println("Exception happening");
            //throw new FileSizeLimitExceededException("Exceeded",max_file_size,size);
            return new ResponseEntity<>(null,HttpStatus.PAYLOAD_TOO_LARGE);

        }
        try {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Long val = timestamp.getTime();
            final Path path = Path.of(System.getProperty("user.dir") + "/Uploads/file" + File.separator + val);

            //String filePath = System.getProperty("user.dir") + "/Uploads" + File.separator + file.getOriginalFilename();
            FileResponse fileResponse = fileUploadService.uploadFile(path, file, 1,val);
            loggerController.IncomingRequest(ip,"File Upload");
            return new ResponseEntity<>(fileResponse, HttpStatus.OK);

        }
//        catch (FileSizeLimitExceededException e){
//            System.out.println("FException----------------------");
//            return new ResponseEntity<>(null,HttpStatus.PAYLOAD_TOO_LARGE);
//        }
        catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(null,HttpStatus.PAYLOAD_TOO_LARGE);
        }
    }

    @CrossOrigin(origins = {"http://localhost:4200","http://trms.nbr.gov.bd","https://trms.nbr.gov.bd"})
    @GetMapping("/file/{filename}")
    public ResponseEntity<Resource> load(HttpServletRequest request, @PathVariable String filename) {
        String ip = commonService.getIPAddress(request);
        try {
            String [] name = filename.split("\\.");
            Path root = Paths.get("Uploads/file/"+name[0]+File.separator);
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
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Long val = timestamp.getTime();
            final Path path = Path.of(System.getProperty("user.dir") + "/Uploads/photo" + File.separator + val);
            FileResponse fileResponse = fileUploadService.uploadFile(path, file, 0,val);
            loggerController.IncomingRequest(ip,"Photo Upload");
            return new ResponseEntity<>(fileResponse, HttpStatus.OK);

        } catch (Exception e) {
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
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Long val = timestamp.getTime();
            final Path path = Path.of(System.getProperty("user.dir") + "/Uploads/photo" + File.separator + val);
            FileResponse fileResponse = fileUploadService.uploadFile(path, file, 0,val);
            loggerController.IncomingRequest(ip,"Photo Upload");
            User u = userService.getUserByUsername(userDetails.getUsername()).orElse(null);
            if(u!=null){
                u.setPhoto(fileResponse.getFileUri());
                userService.saveUser(u);
                return new ResponseEntity<>(fileResponse, HttpStatus.OK);

            }else{
                return new ResponseEntity<>(fileResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
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
            System.out.println("Line 282:"+fname);
            String [] parts = fname.split("\\.");
            System.out.println("Uploads/photo/"+parts[0]+File.separator);
            Path root = Paths.get("Uploads/photo/"+parts[0]+File.separator);

            //System.out.println(root);
            if(!fname.isEmpty()){
                //System.out.println("Line 290:"+root);
                //System.out.println(fname);
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
