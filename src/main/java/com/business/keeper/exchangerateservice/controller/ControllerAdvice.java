package com.business.keeper.exchangerateservice.controller;

import com.business.keeper.exchangerateservice.model.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(RestClientException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleRestClientException(RestClientException ex){
        return ErrorDto.builder().status("500")
                .title("INTERNAL_SERVER_ERROR")
                .description("Internal error fetching a resource")
                .build();
    }
}
