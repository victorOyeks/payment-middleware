package com.bankservice.services;

import com.bankservice.dto.LoginRequest;
import com.bankservice.dto.LoginResponse;
import com.bankservice.entities.Admin;
import com.bankservice.enums.Role;
import com.bankservice.exceptions.CustomException;
import com.bankservice.repositories.AdminRepository;
import com.bankservice.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private  final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponse authenticate(LoginRequest request) {
        String identifier = request.getUserName();
        Admin baseUser = adminRepository.findByUserName(identifier);
        if (baseUser == null) {
            throw new CustomException("User(s) with " + identifier + " does not exist");
        }
        if (passwordEncoder.matches(request.getPassword(), baseUser.getPassword())) {
            return performLogin(request, baseUser.getRole());
        }
        throw new CustomException("Incorrect Credentials!!!");
    }

    private LoginResponse performLogin(LoginRequest loginRequest, Role role) {
        String identifier = loginRequest.getUserName();
        String password = loginRequest.getPassword();
        String accessToken = jwtService.generateToken(createAuthentication(identifier, password));

        SecurityContextHolder.getContext().setAuthentication(createAuthentication(identifier, password));

        return LoginResponse.builder()
                .token(accessToken)
                .role(role.getName())
                .build();
    }

    private Authentication createAuthentication(String username, String password) {
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
