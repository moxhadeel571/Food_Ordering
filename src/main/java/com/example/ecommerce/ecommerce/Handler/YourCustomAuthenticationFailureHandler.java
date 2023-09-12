
/*
package com.example.ecommerce.ecommerce.Handler;//package com.example.job_portal_master.Handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Calendar;

@Component
public abstract class YourCustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

 @Override
 public void onAuthenticationFailure(HttpServletRequest request,
                                     HttpServletResponse response,
                                     AuthenticationException e) throws IOException {

       response.setStatus(HttpStatus.UNAUTHORIZED.value());

       String errorMessage = "Authentication failed: " + e.getMessage();
       String timestamp = Calendar.getInstance().getTime().toString();


   String jsonPayload = "{"
           + "\"message\": \"" + errorMessage + "\","
           + "\"timestamp\": \"" + timestamp + "\","
           + "\"stackTrace\": [";


  for (StackTraceElement element : e.getStackTrace()) {
      jsonPayload += "\"" + element.toString() + "\",";
   }

    // Remove the trailing comma and close the JSON payload
    jsonPayload = jsonPayload.substring(0, jsonPayload.length() - 1) + "]}";

      response.setContentType("application/json");
      response.getOutputStream().println(jsonPayload);
   }

}
*/