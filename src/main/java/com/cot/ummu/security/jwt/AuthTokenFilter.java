package com.cot.ummu.security.jwt;

import com.cot.ummu.security.service.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailServiceImpl userDetailService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            //-1 from every request , we will get JWT
            String jwt = parseJwt(request);
            //validate JWT
            if (jwt != null && jwtUtils.validateToken(jwt)) {
            //3-we need username to get user information
            String  username = jwtUtils.getUserNameFromToken(jwt);
            //4-check DB fetch user and upgrade it to userdetails
            UserDetails userDetails = userDetailService.loadUserByUsername(username);
            //5-set attribute with username
            request.setAttribute("username",username);

            /*
            7session 26,48 dk kaldım
            */



            }




        }

    }

    //Authorization -> Bearer lulusilmieyvmsilymkvlmkaylmkiealmkiyemakhg (token söyle bir sey olacak )
    private String parseJwt(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")){
            return authHeader.substring(7);
        }
        return null;
    }
}
