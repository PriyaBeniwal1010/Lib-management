package com.eg.HousingLibrary.service;

import com.eg.HousingLibrary.utility.*;
import com.eg.HousingLibrary.dto.AuthRequestDTO;
import com.eg.HousingLibrary.dto.AuthResponseDTO;
import com.eg.HousingLibrary.exception.UserNotFoundException;
import com.eg.HousingLibrary.model.User;
import com.eg.HousingLibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LibUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User with this email id doesn't exist"));
        Set<GrantedAuthority> authorities=user.getRoles().stream()
                .map(role-> {
                    return new SimpleGrantedAuthority(role.getName().toString());
                }).collect(Collectors.toSet());
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }

    public AuthResponseDTO login(AuthRequestDTO authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // ✅ Pass the UserDetails object instead of String
        String token = jwtUtil.generateToken(userDetails);

        return new AuthResponseDTO(token, jwtUtil.getExpirationDate(token).toString());
    }
}
