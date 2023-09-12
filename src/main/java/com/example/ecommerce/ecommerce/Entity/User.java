package com.example.ecommerce.ecommerce.Entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.data.annotation.Id;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Document(collection = "users")
public class User {
    @Id
    private String id; // Change the data type to String or ObjectId
    private String fullName; // Corrected variable name to follow Java conventions
    private String gender;
    private String confirmPass; // Corrected variable name to follow Java conventions
    private String mobileNo; // Corrected variable name to follow Java conventions
    private String password;
    private String email;
    private String[] roles;
    private List<String> bookmarkedCandidates;


    public <R> User(String email, String password, R collect) {
    }
}
