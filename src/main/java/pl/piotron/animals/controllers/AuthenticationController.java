package pl.piotron.animals.controllers;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.piotron.animals.model.User;
import pl.piotron.animals.repositories.UserRepository;
import pl.piotron.animals.security.CustomUserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@RestController
public class AuthenticationController {
  private final UserRepository userRepository;

  @Autowired
  public AuthenticationController(UserRepository userRepository) {
    this.userRepository = userRepository;

  }

    @RequestMapping("/user-login")
    @ResponseBody
    public Principal login( Principal user)
    {
        return user;
    }

    @RequestMapping(value = "/user")
    public ResponseEntity<User> loggedUser(Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedUsername = auth.getName();
        User user = userRepository.findByEmail(loggedUsername);
        return ResponseEntity.ok(user);
    }



}









