package project.big.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Обновлённый способ отключения CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register", "/auth/login").permitAll() // Разрешаем доступ к этим маршрутам без авторизации
                        .anyRequest().authenticated() // Все остальные запросы требуют авторизации
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/") // Указываем путь для успешного входа
                        .permitAll()
                );

        return http.build();
    }
}