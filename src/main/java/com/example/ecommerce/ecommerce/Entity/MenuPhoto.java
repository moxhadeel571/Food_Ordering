package com.example.ecommerce.ecommerce.Entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Document(collection = "MenuPhoto")
public  class MenuPhoto {
    @MongoId
    private ObjectId id;
    private String imageUrl;

}
