package pl.piotron.animals.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.piotron.animals.model.User;
import pl.piotron.animals.repositories.UserRepository;

import java.security.Principal;



@RestController
public class AuthenticationController {
  private final UserRepository userRepository;
  @Autowired
  public AuthenticationController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

    @RequestMapping("/user-login")
    @ResponseBody
    public Principal login(Principal user)
    {
        return user;
    }

    @RequestMapping(value = "/user")
    public User loggedUser(Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedUsername = auth.getName();
        return userRepository.findByEmail(loggedUsername);
    }




}









