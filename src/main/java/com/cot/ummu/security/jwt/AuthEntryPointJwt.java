package com.cot.ummu.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    public static final Logger LOGGER = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    /**
     * this class handle exceptions in security- and returns-readable response.
     * @param request that resulted in an <code>AuthenticationException</code>
     * @param response so that the user agent can begin authentication
     * @param authException that caused the invocation
     */

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        LOGGER.error("Uhauthorized access : {}",authException.getMessage() );
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        final Map<String, Object> body = new HashMap<>();
        body.put("status",HttpServletResponse.SC_UNAUTHORIZED);
        //body.put("Adam gibi şifreni gir ","Akıllı ol");da yazsak calısır
        body.put("error","Unauthorized access");
        body.put("message",authException.getMessage());
        body.put("path",request.getServletPath());
        final ObjectMapper mapper = new ObjectMapper();//java objesini json a cevirmek icin
        mapper.writeValue(response.getOutputStream(),body);

    }
}
