package cn.wq.sms.config;

import cn.wq.sms.handle.JWTInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author DengWeiPing
 * @version 1.0
 * @date 2021/6/5 15:35
 */
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
                .excludePathPatterns("/api/user/login") // 登录接口不用于token验证
                .addPathPatterns("/api/**"); // 其他非登录接口都需要进行token验证
    }
}
