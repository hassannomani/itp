package com.nbr.trp.log;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@CrossOrigin(origins = "*", maxAge = 4800)
@RestController
@RequestMapping("/api/v1/logs")
public class LogFetchingController implements ResourceLoaderAware {

    @Autowired
    ServletContext context;

    private ResourceLoader resourceLoader;

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/all-single")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getLogs(HttpServletRequest request) throws IOException {


        String absolutePath = new FileSystemResource("").getFile().getAbsolutePath();
        System.out.println("file:"+ absolutePath+"/logs/app-logback.log");
        Resource resource = resourceLoader.getResource("file:"+ absolutePath+"/logs/app-logback.log");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            //return ;
            //return ResponseEntity.ok(reader.lines().collect(Collectors.joining("\\n")));
            StringBuilder sb = new StringBuilder();
            String line;
            int i=0;
            sb.append("[");
            while ((line = reader.readLine()) != null){
                sb.append(line+","+"\n");
                //System.out.println("Line "+(++i)+line);
            }
            sb.deleteCharAt(sb.length()-2);
            sb.append("]");
            String s = sb.toString();
//            Gson gson = new Gson();
//            String jsonified = gson.toJson(s);
            //System.out.println(String.valueOf(sb));
            //JSONArray js = new JSONArray(s);
            return ResponseEntity.ok(s);


        }


    }

    @GetMapping("/login-single")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getLogIns(HttpServletRequest request) throws IOException {
        String logs = getLogsByEvent("login","app-logback.log");
        return ResponseEntity.ok(logs);

    }

    @GetMapping("/jwt-single")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getJwtError(HttpServletRequest request) throws IOException {
        String logs = getLogsByEvent("unauth_url_jwt_attempt","sever-logger.log");
        return ResponseEntity.ok(logs);

    }

    @GetMapping("/login-error-single")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getLogInError(HttpServletRequest request) throws IOException {
        String logs = getLogsByEvent("login_error","severe-logger.log");
        return ResponseEntity.ok(logs);

    }
    private String getLogsByEvent(String event, String filename) throws IOException{
        String absolutePath = new FileSystemResource("").getFile().getAbsolutePath();
        Resource resource = resourceLoader.getResource("file:"+ absolutePath+"/logs/"+filename);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

            StringBuilder sb = new StringBuilder();
            String line;
            int i = 0;
            sb.append("[");
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(",");
                for (int j = 0; j < arr.length; j++) {
                    if (arr[j].contains("event_type")){
                        String variable = arr[j];
                        if(variable.contains(event))
                            sb.append(line + "," + "\n");
                    }

                }

            }
            if(sb.length()>2)
                sb.deleteCharAt(sb.length() - 2);
            sb.append("]");
            System.out.println(sb);
            return sb.toString();

        }

    }


}
