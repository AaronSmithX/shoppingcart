package org.wecancodeit.shoppingcart.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    CustomHandlerInterceptor customHandlerInterceptor() {
         return new CustomHandlerInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customHandlerInterceptor());
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

		// Register resource handler for CSS, JS, and images
		registry.addResourceHandler("/**") // for ANY request from web root...
			.addResourceLocations("classpath:/static/") // try to serve corresponding resource from /static/
			.setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic()); // with a 2 hour cache
    }
}
