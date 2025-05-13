package jnu_ddobuk.fruitsfarm_BE.web.config;

import jnu_ddobuk.fruitsfarm_BE.web.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:3000",
                        "https://fruitsfarm.com",
                        "http://fruitsfarm.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .maxAge(3000); // pre-flight 요청을 캐시할 시간 (초 단위)
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1) // 실행 순서 지정 (낮을수록 먼저 실행)
                .addPathPatterns("/**") // 모든 요청에 대해 인터셉터 적용
                .excludePathPatterns(
                        "/api/v1/signup",
                        "/api/v1/logout",
                        "/api/v1/login",
                        "/api/v1/check-accountId",
                        "/api/v1/error",
                        "/swagger-ui/**",
                        "/v3/api-docs/**");
        //인터셉터에서 제외
    }

}
