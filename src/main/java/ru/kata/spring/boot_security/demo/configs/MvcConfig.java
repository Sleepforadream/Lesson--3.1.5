package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import ru.kata.spring.boot_security.demo.controller.Converters;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.HashMap;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private final Converters.StringToRoleConverter stringToRoleConverter;

    private final Converters.StringsArrayToRoleConverter stringsArrayToRoleConverter;

    @Autowired
    public MvcConfig(Converters.StringsArrayToRoleConverter stringsArrayToRoleConverter,
                     Converters.StringToRoleConverter stringToRoleConverter) {
        this.stringToRoleConverter = stringToRoleConverter;
        this.stringsArrayToRoleConverter = stringsArrayToRoleConverter;
    }

    @Bean
    public ClassLoaderTemplateResolver templateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setCheckExistence(true);
        return templateResolver;
    }

    @Bean
    public ClassLoaderTemplateResolver templateResolver2() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setCheckExistence(true);
        return templateResolver;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToRoleConverter);
        registry.addConverter(stringsArrayToRoleConverter);
    }

}
