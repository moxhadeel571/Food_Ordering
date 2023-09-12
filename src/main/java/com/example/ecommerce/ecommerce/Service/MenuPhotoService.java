package com.example.ecommerce.ecommerce.Service;

import com.example.ecommerce.ecommerce.Entity.MenuPhoto;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface MenuPhotoService {
     abstract ObjectId savePhoto(MultipartFile photoData, MenuPhoto menuPhoto) throws IOException;
    String getContentType(String id);
    String getFileName(String id);
    byte[] getFileData(String id);
    MenuPhoto getAllPhoto();
}
