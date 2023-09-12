package com.example.ecommerce.ecommerce.Implementation;

import com.example.ecommerce.ecommerce.Entity.MenuPhoto;
import com.example.ecommerce.ecommerce.Service.MenuPhotoService;
import org.bson.Document;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
public class MenuPhotoImpl implements MenuPhotoService {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public MenuPhotoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public ObjectId savePhoto(MultipartFile photoData, MenuPhoto menuPhoto) throws IOException {
        if (photoData == null) {
            throw new IllegalArgumentException("File cannot be null.");
        }
        byte[] fileData = photoData.getBytes();
        String fileName = photoData.getOriginalFilename();
        String contentType = photoData.getContentType();
        Document menu = new Document();
        menu.append("imageUrl",menuPhoto.getImageUrl());
        menu.append("contentType",contentType);
        menu.append("fileName",fileName);
        menu.append("contentType",contentType);
        menu.append("fileData",fileData);
        mongoTemplate.getCollection("MenuPhoto").insertOne(menu);
        return menu.getObjectId("_id");

    }
    @Override
    public String getContentType(String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        Document candidateDocument = mongoTemplate.findOne(query, Document.class, "MenuPhoto");

        if (candidateDocument != null) {
            return candidateDocument.getString("contentType");
        }

        return null;
    }

    @Override
    public String getFileName(String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        Document candidateDocument = mongoTemplate.findOne(query, Document.class, "MenuPhoto");

        if (candidateDocument != null) {
            return candidateDocument.getString("fileName");
        }

        return null;
    }

    @Override
    public byte[] getFileData(String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        Document candidateDocument = mongoTemplate.findOne(query, Document.class, "MenuPhoto");

        if (candidateDocument != null) {
            Binary fileData = candidateDocument.get("fileData", Binary.class);
            if (fileData != null) {
                return fileData.getData();
            }
        }

        return null;
    }

    @Override
    public MenuPhoto getAllPhoto() {
        Query query = new Query();
        query.limit(1); // Limit the result to one document
        MenuPhoto menuPhoto = mongoTemplate.findOne(query, MenuPhoto.class);
        return menuPhoto;
    }


}
