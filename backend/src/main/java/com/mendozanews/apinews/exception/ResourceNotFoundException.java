package com.mendozanews.apinews.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s con %s='%s' no encontrado", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue, Integer dias) {
        super(String.format("No hay %s con %s='%s' en los ultimos %d", resourceName, fieldName, fieldValue, dias));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public ResourceNotFoundException(String resourceName) {
        super(String.format("No hay registros de '%s'", resourceName));
        this.resourceName = resourceName;
    }

    public ResourceNotFoundException(String resourceName, Integer dias) {
        super(String.format("No hay %s en los ultimos %d", resourceName, dias));
        this.resourceName = resourceName;
    }
}
