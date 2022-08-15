package com.kafein.intern.warehouse.contoller;

import com.kafein.intern.warehouse.dto.LoginDTO;
import com.kafein.intern.warehouse.entity.User;
import com.kafein.intern.warehouse.security.JwtTokenProvider;
import com.kafein.intern.warehouse.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login (@RequestBody LoginDTO login) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(login.getUsername(),login.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtTokenProvider.generateJwtToken(authentication);
        return "Bearer " + jwtToken;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginDTO login) {
        if (userService.getUserByUsername(login.getUsername()) != null) {
            return new ResponseEntity<>("User name is already in use", HttpStatus.BAD_REQUEST);
        }
        else {
            User user = new User();
            user.setPassword(passwordEncoder.encode(login.getPassword()));
            user.setUsername(login.getUsername());
            userService.saveUser(user);
            return new ResponseEntity<>("Account is successfully created", HttpStatus.CREATED);
        }
    }

}
