package com.nbr.trp.test.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nbr.trp.ledger.entity.LedgerAdminView;
import com.nbr.trp.user.response.MessageResponse;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ClassPathResource;
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
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 4800)
@RestController
@RequestMapping("/api/v1/testcontroller")
public class testController implements ResourceLoaderAware {

    @Autowired
    ServletContext context;

    private ResourceLoader resourceLoader;

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/logs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getLogs(HttpServletRequest request) throws IOException {


        String absolutePath = new FileSystemResource("").getFile().getAbsolutePath();
        Resource resource = resourceLoader.getResource("file:"+ absolutePath+"/logs/app-logback.log");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            //return ;
            //return ResponseEntity.ok(reader.lines().collect(Collectors.joining("\\n")));
            StringBuilder sb = new StringBuilder();
            String line;
            int i=0;
            sb.append("[");
            while ((line = reader.readLine()) != null){
                sb.append(","+line+"\n");
                //System.out.println("Line "+(++i)+line);
            }
            sb.deleteCharAt(1);
            sb.append("]");
            //System.out.println(String.valueOf(sb));
            JsonArray obj = new JsonParser().parse(String.valueOf(sb)).getAsJsonArray();
            return ResponseEntity.ok(obj);


        }


    }
}
