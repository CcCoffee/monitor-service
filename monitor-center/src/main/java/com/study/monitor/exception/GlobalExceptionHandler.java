package com.study.monitor.exception;

import com.study.monitor.modal.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiResponse<Object> handleException(Exception e) {
        LOGGER.error("An unexpected error occurred", e);
        return ApiResponse.error(500, "服务器内部错误");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiResponse<Object> handleValidationException(MethodArgumentNotValidException e) {
        LOGGER.error("Validation exception occurred", e);
        return ApiResponse.badRequest(e.getMessage());
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    @ResponseBody
    public ApiResponse<Object> handleResourceNotFoundException(HttpClientErrorException.NotFound e) {
        LOGGER.error("Resource not found exception occurred", e);
        return ApiResponse.error(404, "资源未找到");
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    @ResponseBody
    public ApiResponse<Object> handleUnauthorizedException(HttpClientErrorException.Unauthorized e) {
        LOGGER.error("Unauthorized exception occurred", e);
        return ApiResponse.unauthorized("未授权");
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    @ResponseBody
    public ApiResponse<Object> handleAccessDeniedException(HttpClientErrorException.Forbidden e) {
        LOGGER.error("Access denied exception occurred", e);
        return ApiResponse.forbidden("禁止访问");
    }

    // Add other custom exception handling methods

    // ...
}