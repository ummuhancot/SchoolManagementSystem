package com.cot.ummu.service.user;

import com.cot.ummu.payload.request.authentication.LoginRequest;
import com.cot.ummu.payload.response.authentication.AuthenticationResponse;
import com.cot.ummu.repository.user.UserRepository;

import com.cot.ummu.securtiy.jwt.JwtUtils;
import com.cot.ummu.securtiy.service.UserDetailsImpl;
import com.cot.ummu.service.helpar.MethodHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MethodHelper methodHelper;


    public AuthenticationResponse authenticate(@Valid LoginRequest loginRequest) {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        //injection of security in service
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,password));
        //Security authentication bu satırda yapılıyor
        SecurityContextHolder.getContext().setAuthentication(authentication);


        String token = jwtUtils.generateToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String userRole = userDetails.getAuthorities().iterator().next().getAuthority();

        return AuthenticationResponse.builder()
                .token(token)
                .role(userRole)
                .username(userDetails.getUsername())
                .build();
    }
}
