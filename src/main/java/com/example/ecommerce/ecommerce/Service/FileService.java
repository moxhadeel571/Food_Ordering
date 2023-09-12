package com.example.ecommerce.ecommerce.Service;

import com.example.ecommerce.ecommerce.Entity.Restaurant;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface FileService {
    ObjectId savemenu(MultipartFile file, Restaurant restaurant) throws IOException;

    String getContentType(String staticCandidateId);

    String getFileName(String staticCandidateId);

    byte[] getFileData(String staticCandidateId);




//    ResponseEntity<GridFsResource> getResumeFile(String fileName);
}
