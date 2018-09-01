package com.github.munerf.modelmapperspringbootstarter;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.Set;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass(JpaRepository.class)
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

            Class<?> javaType = entity.getIdType().getJavaType();
            Class destinationType = entity.getJavaType();

            Converter<Class<?>, Object> toEntityConverter = context -> context.getSource() != null ? entityManager.find(entity.getJavaType(), context.getSource()) : null;
            modelMapper.emptyTypeMap(javaType, destinationType).setConverter(toEntityConverter);

            Converter<Object, Object> toDtoConverter = context -> context.getSource() != null ? StarterUtils.getEntityId(context.getSource()) : null;
            modelMapper.emptyTypeMap(destinationType, javaType ).setConverter(toDtoConverter);

            /* ModelMapper API will have the following method in release 2.1.1 */
            //modelMapper.addConverter(toEntityConverter, sourceType, destinationType);
        }

        return modelMapper;
    }



}
