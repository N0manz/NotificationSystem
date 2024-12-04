package project.big.main.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Применять CORS ко всем маршрутам
                .allowedOrigins("http://localhost:3000") // Адрес вашего фронтенда
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Разрешённые HTTP-методы
                .allowedHeaders("*") // Разрешить любые заголовки
                .allowCredentials(true); // Если нужно использовать куки
    }
}
