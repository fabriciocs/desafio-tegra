package br.com.tegra.config;

import io.github.jhipster.config.locale.AngularCookieLocaleResolver;
import org.apache.tika.Tika;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class FileConfiguration {

    @Bean
    public Tika tika() {
       return new Tika();
    }
}
