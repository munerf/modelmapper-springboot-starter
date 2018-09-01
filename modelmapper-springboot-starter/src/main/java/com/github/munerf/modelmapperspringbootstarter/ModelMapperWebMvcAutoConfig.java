package com.github.munerf.modelmapperspringbootstarter;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import javax.persistence.EntityManager;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass(JpaRepository.class)

public class ModelMapperWebMvcAutoConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;
    private final EntityManager entityManager;
    private final ModelMapper modelMapper;

    @Autowired
    public ModelMapperWebMvcAutoConfig(ApplicationContext applicationContext, EntityManager entityManager, ModelMapper modelMapper) {
        this.applicationContext = applicationContext;
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().applicationContext(this.applicationContext).build();
        argumentResolvers.add(new RequestResponseBodyDtoMethodProcessor(objectMapper, entityManager, modelMapper));
    }



}
