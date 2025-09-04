package com.nbr.trp.common.service;

import com.nbr.trp.common.entity.FileResponse;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
@Service
public interface FileUploadService {

    FileResponse uploadFile(Path path, MultipartFile file, int flag, Long val);

    Resource retrieve(Path p, String filename, int flag);
}
