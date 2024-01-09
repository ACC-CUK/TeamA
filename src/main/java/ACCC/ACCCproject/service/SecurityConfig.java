package ACCC.ACCCproject.service;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
@ComponentScan("ACCC.ACCCproject")
public class SecurityConfig{
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/getCsrfToken").permitAll()
                        .requestMatchers("/users/login", "/resources/**").permitAll()
                        .anyRequest().authenticated()
                )
//                .csrf(csrf -> csrf
//                        .csrfTokenRepository(sessionCsrfRepository()))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(login -> login
                        .loginPage("/login/index.html")
                        .permitAll()
                )
                .logout(withDefaults());

        return http.build();
    }

    @Bean
    HttpSessionCsrfTokenRepository sessionCsrfRepository() {
        HttpSessionCsrfTokenRepository csrfRepository = new HttpSessionCsrfTokenRepository();

        // HTTP 헤더에서 토큰을 인덱싱하는 문자열 설정
        csrfRepository.setHeaderName("X-CSRF-TOKEN");
        // URL 파라미터에서 토큰에 대응되는 변수 설정
        csrfRepository.setParameterName("_csrf");
        // 세션에서 토큰을 인덱싱 하는 문자열을 설정. 기본값이 무척 길어서 오버라이딩 하는 게 좋아요.
        // 기본값: "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN"
        csrfRepository.setSessionAttributeName("X-CSRF-TOKEN");

        return csrfRepository;
    }

//    @Bean
//    CookieCsrfTokenRepository cookieCsrfRepository() {
//        CookieCsrfTokenRepository csrfRepository = new CookieCsrfTokenRepository();
//
//        csrfRepository.setCookieHttpOnly(false);
//        csrfRepository.setHeaderName("X-CSRF-TOKEN");
//        csrfRepository.setParameterName("_csrf");
//        csrfRepository.setCookieName("XSRF-TOKEN");
//        //csrfRepository.setCookiePath("..."); // 기본값: request.getContextPath()
//
//        return csrfRepository;
//    }

}
