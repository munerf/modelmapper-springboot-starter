package com.github.munerf.modelmapperspringbootstarter;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.InheritingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

@Configuration
@ConditionalOnWebApplication
@Import(WebMvcConfig.class)
public class ModelMapperAutoConfiguration {

    private final EntityManager entityManager;

    @Autowired
    public ModelMapperAutoConfiguration(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Bean
    ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        for (EntityType<?> entity : entities) {

            Class sourceType = Long.class;
            Class destinationType = entity.getJavaType();

            Converter<Long, Object> converter = context -> context.getSource() != null ? entityManager.find(entity.getJavaType(), context.getSource()) : null;

            /* ModelMapper API will have the following method in release 2.1.1 */
            //modelMapper.addConverter(converter, sourceType, destinationType);

            // waiting for API update
            modelMapper.emptyTypeMap(sourceType, destinationType).setConverter(converter);

        }

        return modelMapper;
    }
}
