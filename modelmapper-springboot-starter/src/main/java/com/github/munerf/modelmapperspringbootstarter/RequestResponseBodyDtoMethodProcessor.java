package com.github.munerf.modelmapperspringbootstarter;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collections;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

public class RequestResponseBodyDtoMethodProcessor extends RequestResponseBodyMethodProcessor {

    private final ModelMapper modelMapper;
    private final EntityManager entityManager;

    public RequestResponseBodyDtoMethodProcessor(ObjectMapper objectMapper, EntityManager entityManager,
                                                 ModelMapper modelMapper) {
        super(Collections.singletonList(new MappingJackson2HttpMessageConverter(objectMapper)));
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestResponseBodyDto.class);
    }

    @Override
    protected void validateIfApplicable(WebDataBinder binder, MethodParameter parameter) {
        binder.validate();
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        Object dto = super.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
        Object id = StarterUtils.getEntityId(dto);
        if (id == null) {
            return modelMapper.map(dto, parameter.getParameterType());
        } else {
            Object persistedObject = entityManager.find(parameter.getParameterType(), id);
            modelMapper.map(dto, persistedObject);
            return persistedObject;
        }

    }

    @Override
    protected Object readWithMessageConverters(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType)
            throws IOException, HttpMediaTypeNotSupportedException, HttpMessageNotReadableException {

        for (Annotation ann : parameter.getParameterAnnotations()) {
            RequestResponseBodyDto requestResponseBodyDtoType = AnnotationUtils.getAnnotation(ann, RequestResponseBodyDto.class);
            if (requestResponseBodyDtoType != null) {
                return super.readWithMessageConverters(inputMessage, parameter, requestResponseBodyDtoType.value());
            }
        }

        throw new RuntimeException();
    }
}
