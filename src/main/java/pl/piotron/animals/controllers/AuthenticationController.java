package pl.piotron.animals.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.piotron.animals.model.User;
import pl.piotron.animals.repositories.UserRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
public class AuthenticationController {
  private final UserRepository userRepository;
  @Autowired
  public AuthenticationController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PostMapping(value = "/user-login")
  public ResponseEntity<Map<String, Object>> login(@RequestParam String email, @RequestParam String password) throws IOException {
    String token = null;
    User appUser = userRepository.findByEmail(email);
    Map<String, Object> tokenMap = new HashMap<String, Object>();
    if (appUser != null && appUser.getPassword().equals(password)) {
      token = Jwts.builder().setSubject(email).claim("roles", appUser.getRoles()).setIssuedAt(new Date())
              .signWith(SignatureAlgorithm.HS256, "secretkey").compact();
      tokenMap.put("token", token);
      tokenMap.put("user", appUser);
      return new ResponseEntity<Map<String, Object>>(tokenMap, HttpStatus.OK);
    } else {
      tokenMap.put("token", null);
      return new ResponseEntity<Map<String, Object>>(tokenMap, HttpStatus.UNAUTHORIZED);
    }
  }

    /*@RequestMapping(value = "/user")
    public User loggedUser(Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedUsername = auth.getName();
        return userRepository.findByEmail(loggedUsername);
    }*/




}









