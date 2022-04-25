package com.gumo.temps.controller;

import com.gumo.temps.model.ConfirmationToken;
import com.gumo.temps.model.Role;
import com.gumo.temps.model.RoleEnum;
import com.gumo.temps.model.User;
import com.gumo.temps.repository.ConfirmationTokenRepository;
import com.gumo.temps.repository.RoleRepository;
import com.gumo.temps.repository.UserRepository;
import com.gumo.temps.request.LoginRequest;
import com.gumo.temps.request.RegisterRequest;
import com.gumo.temps.response.JwtResponse;
import com.gumo.temps.response.MessageResponse;
import com.gumo.temps.security.jwt.JwtUtils;
import com.gumo.temps.security.service.UserDetailsImplementation;
import com.gumo.temps.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    EmailSenderService emailSenderService;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("user_login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImplementation userDetails = (UserDetailsImplementation) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item->item.getAuthority())
                .collect(Collectors.toList());
        return  ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));

    }

    @PostMapping("user_register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest){
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("This username is already in used."));
        }
        if(userRepository.existsByEmail(registerRequest.getEmail())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("This email is already in used."));
        }

        //create account for user, default is not verified
        User user = new User(registerRequest.getUsername(),
                registerRequest.getEmail(),
                encoder.encode(registerRequest.getPassword()),
                0);

        Set<String> role = registerRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if(role == null) {
            Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                    .orElseThrow(()->new RuntimeException("Role not found"));
            roles.add(userRole);
        } else {
            role.forEach(eachRole -> {
                switch (eachRole) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
                                .orElseThrow(()-> new RuntimeException("Role not available"));
                        roles.add(adminRole);
                        break;

                    default:
                        Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                                .orElseThrow(()->new RuntimeException("Role not found"));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        //email verification token
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);

        //send email
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Email Verification from Temps-tation");
        mailMessage.setFrom("hello@tempstation.com");
        mailMessage.setText("Click here to verify your account: " +
                "http://localhost:8080/confirm-email?token=" +
                confirmationToken.getToken());

        emailSenderService.sendEmail((mailMessage));

        return ResponseEntity.ok(new MessageResponse("Successfully registered."));
    }

    @RequestMapping(value = "/confirm_email", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserEmail(@RequestParam("token")String confirmationToken){

        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            User user = userRepository.findByEmailIdIgnoreCase(token.getUser().getEmail());
            user.setVerified(1);
            userRepository.save(user);
        }
        else
        {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("This link is invalid."));
        }

        return ResponseEntity.ok(new MessageResponse("Successfully confirm email."));
    }


}
