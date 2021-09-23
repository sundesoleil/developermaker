package com.developers.developermaker.exception;

import com.developers.developermaker.dto.DMakerErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class DMakerExceptionHandler {

    @ExceptionHandler(DMakerException.class)
    public DMakerErrorResponse handleException(
            DMakerException e,
            HttpServletRequest request
    ) {
        log.error("errorCode: {}, url: {}, message: {}",
                e.getDMakerErrorCode(),
                request.getRequestURI(),
                e.getDetailMessage()
        );

        return DMakerErrorResponse.builder()
                .errorCode(e.getDMakerErrorCode())
                .errorMessage(e.getDetailMessage())
                .build();
    }
}
