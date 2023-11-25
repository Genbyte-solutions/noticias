package com.mendozanews.apinews.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourceBadRequestException extends RuntimeException{

    public ResourceBadRequestException(String mensaje) {
        super(mensaje);
    }
}
