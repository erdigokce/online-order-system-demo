package com.onlineordersystem.config;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ExpressionLanguageFeatureLevel;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class WebConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(
            "classpath:/messages/user/messages",
            "classpath:/messages/seller/messages",
            "classpath:/messages/order/messages",
            "classpath:/messages/common/messages",
            "classpath:/messages/product/messages"
        );
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setFallbackToSystemLocale(true);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public ValidatorFactory validatorFactory() {
        return Validation.byProvider(HibernateValidator.class)
            .configure()
            .constraintExpressionLanguageFeatureLevel(ExpressionLanguageFeatureLevel.BEAN_PROPERTIES)
            .buildValidatorFactory();
    }

    @Scope("prototype")
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
