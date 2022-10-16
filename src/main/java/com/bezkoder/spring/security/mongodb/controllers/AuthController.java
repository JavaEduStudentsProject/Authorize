package com.bezkoder.spring.security.mongodb.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.bezkoder.spring.security.mongodb.kafka.MessageListener;
import com.bezkoder.spring.security.mongodb.kafka.MessageProducer;
import com.bezkoder.spring.security.mongodb.kafka.MessageUserProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.bezkoder.spring.security.mongodb.models.ERole;
import com.bezkoder.spring.security.mongodb.models.Role;
import com.bezkoder.spring.security.mongodb.models.User;
import com.bezkoder.spring.security.mongodb.payload.request.LoginRequest;
import com.bezkoder.spring.security.mongodb.payload.request.SignupRequest;
import com.bezkoder.spring.security.mongodb.payload.response.UserInfoResponse;
import com.bezkoder.spring.security.mongodb.payload.response.MessageResponse;
import com.bezkoder.spring.security.mongodb.repository.RoleRepository;
import com.bezkoder.spring.security.mongodb.repository.UserRepository;
import com.bezkoder.spring.security.mongodb.security.jwt.JwtUtils;
import com.bezkoder.spring.security.mongodb.security.services.UserDetailsImpl;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  MessageProducer mp;

  @Autowired
  MessageUserProducer messageUserProducer;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  MessageListener ml;

  @Autowired
  private MongoTemplate mt;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    ResponseEntity<UserInfoResponse> responseEntity = ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            .body(new UserInfoResponse(userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles)
            );
    log.info("User {} sign in", Objects.requireNonNull(responseEntity.getBody()).getUsername());
    return responseEntity;
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
//    Role nj = new Role(ERole.ROLE_ADMIN);
//    Role nj1 = new Role(ERole.ROLE_USER);
//    Role nj2 = new Role(ERole.ROLE_MODERATOR);
//    roleRepository.save(nj);
//    roleRepository.save(nj1);
//    roleRepository.save(nj2);
    log.info("Try sign up user {}", signUpRequest.getUsername());
    List<User> list= mt.findAll(User.class);
    for (User l: list) {
      if(l.getUsername().equals(signUpRequest.getUsername())) {
        log.info("Username {} is already taken", l.getUsername());
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Username is already taken!"));
      }
      if(l.getEmail().equals(signUpRequest.getEmail())) {
        log.info("Email {} is already taken", l.getEmail());
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Email is already taken!"));
      }
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(),
                         signUpRequest.getEmail(),
                         encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRoles();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        if ("admin".equals(role)) {
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);
        } else {
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }
    user.setRoles(roles);
    messageUserProducer.sendMessage(user, "SaveUser");
    log.info("The user {} was sent to Database for save", user.getUsername());
    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
}
