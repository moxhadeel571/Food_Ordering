package com.example.ecommerce.ecommerce.Implementation;

import com.example.ecommerce.ecommerce.Entity.User;
import com.example.ecommerce.ecommerce.Repository.UserRepository;
import com.example.ecommerce.ecommerce.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {
      @Autowired
    private UserRepository userRepository;
      @Autowired
      private BCryptPasswordEncoder bcryptPasswordEncoder;

    @Override
    public User saveUser(User User) {

        String passsword=   bcryptPasswordEncoder.encode(User.getPassword());
        User.setPassword(passsword);
        User newUser= userRepository.save(User);
        return newUser;
    }

    @Override
    public void removeSuccessMessage() {
    HttpSession session =    ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest().getSession();
    session.removeAttribute("msg");
    }





}
