package com.eg.HousingLibrary.controller;

import com.eg.HousingLibrary.dto.AuthRequestDTO;
import com.eg.HousingLibrary.dto.AuthResponseDTO;
import com.eg.HousingLibrary.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO request) {
        String token = String.valueOf(authService.login(request));
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequestDTO requestDTO){
         authService.register(requestDTO);
         return ResponseEntity.ok("User registered successfully");
    }


}
