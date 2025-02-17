package com.eg.HousingLibrary.service;

import com.eg.HousingLibrary.dto.AuthRequestDTO;
import com.eg.HousingLibrary.dto.AuthResponseDTO;
import com.eg.HousingLibrary.dto.UserDTO;
import com.eg.HousingLibrary.model.ERole;
import com.eg.HousingLibrary.model.Role;
import com.eg.HousingLibrary.model.User;
import com.eg.HousingLibrary.repository.RoleRepository;
import com.eg.HousingLibrary.repository.UserRepository;
import com.eg.HousingLibrary.utility.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


import static com.eg.HousingLibrary.model.ERole.ADMIN;
import static com.eg.HousingLibrary.model.ERole.USER;
import static java.util.Set.*;

@Slf4j
@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    // Utility method to format Date as ISO 8601
    private String formatDateToISO(Date date) {
        return date.toInstant()
                .atZone(ZoneId.of("UTC")) // Convert to UTC
                .format(DateTimeFormatter.ISO_INSTANT);
    }


    public AuthResponseDTO login(AuthRequestDTO authRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwUtil.generateToken(userDetails);
        String expirationTime = formatDateToISO(jwUtil.getExpirationDate(token));
        return new AuthResponseDTO(token, expirationTime);
    }


    public AuthResponseDTO register(AuthRequestDTO request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());


        if (existingUser.isPresent()) {
            throw new RuntimeException("User already exists with email: " + request.getEmail());
        }

        ERole assignedRole = (request.getRole() != null && request.getRole().equalsIgnoreCase("ADMIN"))
                ? ERole.ADMIN
                : ERole.USER;

        log.info("Assigned role: " + assignedRole);


        Role userRole = roleRepository.findByName(assignedRole)
                .orElseThrow(() -> new RuntimeException("Error: Role not found"));

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Set.of(userRole));
        user.setProfilePicture(request.getImage_file());
        user.setName(request.getName());


        userRepository.save(user);


        String token = jwUtil.generateToken(user);
        String expirationTime = formatDateToISO(jwUtil.getExpirationDate(token));
        return new AuthResponseDTO(token, expirationTime);
    }


}
