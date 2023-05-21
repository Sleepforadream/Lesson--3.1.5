package ru.kata.spring.web.project.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
@PropertySource("classpath:/application.properties")
public class MvcConfig implements WebMvcConfigurer {
    @Bean
    public ClassLoaderTemplateResolver templateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setCheckExistence(true);
        return templateResolver;
    }

    private final Converters.StringToRoleConverter stringToRoleConverter;

    private final Converters.StringsArrayToRoleConverter stringsArrayToRoleConverter;

    @Autowired
    public MvcConfig(Converters.StringsArrayToRoleConverter stringsArrayToRoleConverter,
                     Converters.StringToRoleConverter stringToRoleConverter) {
        this.stringToRoleConverter = stringToRoleConverter;
        this.stringsArrayToRoleConverter = stringsArrayToRoleConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToRoleConverter);
        registry.addConverter(stringsArrayToRoleConverter);
    }

}
