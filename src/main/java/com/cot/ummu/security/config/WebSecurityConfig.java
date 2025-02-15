package com.cot.ummu.security.config;


import com.cot.ummu.security.jwt.AuthEntryPointJwt;
import com.cot.ummu.security.jwt.AuthTokenFilter;
import com.cot.ummu.security.service.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailServiceImpl userDetailService;

    //diğer pakette oluşturduğumuz security exception classını buraya gömmüş olduk
    private final AuthEntryPointJwt authEntryPointJwt;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        //CORS cross*origin resourse sharing
        http.cors()
                .and()
                .csrf().disable()//cors ve csrf disable edildi
                //configre exception handling
                .exceptionHandling().authenticationEntryPoint(authEntryPointJwt)
                .and()
                //configure session management (session : oturum demek database de bir reqest atıp response döndürürüz bunu bir sesion icinde yapmamız gerek.)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//sesinı oluşturma politikası.stateless yaparak durumunu kaydetmeyeceğiz.Sesion la ilgiliyi hic bir veriyi tutmuyoruz demek
                //confgura allow list
                .and()
                .authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll()
                //other request will be authenticated
                .anyRequest().authenticated();
                //configure frames to be sure from the same origin
                http.headers().frameOptions().sameOrigin();//aynı originde olup olmadığını kontrol eder yani aynı ıp den gelilip gelmediği kontrol edilir
/*
1,26 dk
* */


    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authprovider = new DaoAuthenticationProvider();
        authprovider.setUserDetailsService(userDetailService);
        authprovider.setPasswordEncoder(passwordEncoder());




    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }   //passwordlerin şifrelenmesi için kullanılır



    @Bean
    //authtokenfilter daki methodları bu classta kullanmadığımız icin bean olarak geri verdik yoksa
    //private olarak tanımlaya bilirdik
    //kendi authtokenfilter yerine bizim yazdığımızı kullanmasını sağlar
    public AuthTokenFilter authenticationJwtTokenFilter(){
        return new AuthTokenFilter();
    }


    private static final String[] AUTH_WHITELIST = {
            "/v3/api-docs/**",
            "swagger-ui.html",
            "/swagger-ui/**",
            "/",
            "index.html",
            "/images/**",
            "/css/**",
            "/js/**",
            "/contactMessages/save",
            "/auth/login"
    };

}
