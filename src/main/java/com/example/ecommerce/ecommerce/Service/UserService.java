package com.example.ecommerce.ecommerce.Service;

import com.example.ecommerce.ecommerce.Entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {


 public User saveUser(User User);

 public void removeSuccessMessage();

}