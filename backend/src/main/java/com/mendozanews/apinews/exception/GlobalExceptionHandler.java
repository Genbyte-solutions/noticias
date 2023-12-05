package com.mendozanews.apinews.exception;

import com.mendozanews.apinews.model.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handlerException(Exception exception, WebRequest webRequest) {

        ApiResponse apiResponse = new ApiResponse(exception.getMessage(),
                webRequest.getDescription(false));

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                                             WebRequest webRequest) {
        Map<String, String> mapErros = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach(error -> {
            String clave = ((FieldError) error).getField();
            String valor = error.getDefaultMessage();
            mapErros.put(clave, valor);
        });

        ApiResponse apiResponse = new ApiResponse(mapErros.toString(),
                webRequest.getDescription(false));

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceBadRequestException.class)
    public ResponseEntity<ApiResponse> handlerResourceBadRequestException(ResourceBadRequestException exception,
                                                                          WebRequest webRequest) {
        ApiResponse apiResponse = new ApiResponse(
                exception.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handlerResourceNotFoundException(ResourceNotFoundException exception,
                                                                        WebRequest webRequest) {

        ApiResponse apiResponse = new ApiResponse(
                exception.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }
}
