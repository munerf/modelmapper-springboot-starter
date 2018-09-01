package com.github.munerf.modelmapperspringbootstarter;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;

public class StarterUtils {

    public static Object getEntityId(@NotNull Object dto) {
        for (Field field : dto.getClass().getDeclaredFields()) {
            if (field.getAnnotation(Id.class) != null) {
                try {
                    field.setAccessible(true);
                    return field.get(dto);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

}
