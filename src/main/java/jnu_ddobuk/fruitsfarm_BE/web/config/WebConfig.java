package jnu_ddobuk.fruitsfarm_BE.web.config;

import jnu_ddobuk.fruitsfarm_BE.web.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1) // 실행 순서 지정 (낮을수록 먼저 실행)
                .addPathPatterns("/**") // 모든 요청에 대해 인터셉터 적용
                .excludePathPatterns("/signup", "/login", "/error");
        // 회원가입, 로그인 요청은 인터셉터에서 제외
    }

}
