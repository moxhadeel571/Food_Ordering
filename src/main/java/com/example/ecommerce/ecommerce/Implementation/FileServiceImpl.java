package com.example.ecommerce.ecommerce.Implementation;

import com.example.ecommerce.ecommerce.Entity.Restaurant;
import com.example.ecommerce.ecommerce.Service.FileService;
import org.bson.BsonBinarySubType;
import org.bson.Document;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Component
public class FileServiceImpl implements FileService {

    private final MongoTemplate mongoTemplate;
@Autowired
    public FileServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public ObjectId savemenu(MultipartFile file, Restaurant restaurant) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("file cannot be null");
        }

        byte[] fileData = file.getBytes();
        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        Document randowmDocument=new Document();
        randowmDocument.append("itemName",restaurant.getItemName())
                .append("contentType",restaurant.getFileContentType())
                .append("fileData",new Binary(BsonBinarySubType.BINARY,fileData))
                .append("fileName",fileName)
                .append("fileSize",fileData.length)
                .append("contentType", contentType)
                .append("category",restaurant.getCategory())
                .append("price",restaurant.getPrice())
                .append("ingredients",restaurant.getIngredients())
                .append("addedDate", new Date())  // Current date
                .append("addedTime", new Date()); // Current time
        mongoTemplate.getCollection("restaurants").insertOne(randowmDocument);
        return randowmDocument.getObjectId("_id");
    }

    @Override
    public String getContentType(String staticCandidateId) {
        return null;
    }

    @Override
    public String getFileName(String staticCandidateId) {
        return null;
    }

    @Override
    public byte[] getFileData(String staticCandidateId) {
        return new byte[0];
    }

//


}





