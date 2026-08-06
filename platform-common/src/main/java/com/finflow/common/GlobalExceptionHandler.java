package com.finflow.common;
import org.slf4j.MDC; import org.springframework.http.*; import org.springframework.web.bind.MethodArgumentNotValidException; import org.springframework.web.bind.annotation.*; import java.time.Instant; import java.util.*;
@RestControllerAdvice public class GlobalExceptionHandler{
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<ApiError> validation(MethodArgumentNotValidException e){var d=new LinkedHashMap<String,Object>();e.getBindingResult().getFieldErrors().forEach(x->d.put(x.getField(),x.getDefaultMessage()));return ResponseEntity.badRequest().body(new ApiError(Instant.now(),"VALIDATION_ERROR","Request validation failed",MDC.get("correlationId"),d));}
 @ExceptionHandler(IllegalArgumentException.class) ResponseEntity<ApiError> bad(IllegalArgumentException e){return ResponseEntity.badRequest().body(new ApiError(Instant.now(),"BUSINESS_RULE",e.getMessage(),MDC.get("correlationId"),Map.of()));}
 @ExceptionHandler(Exception.class) ResponseEntity<ApiError> all(Exception e){return ResponseEntity.status(500).body(new ApiError(Instant.now(),"INTERNAL_ERROR","Unexpected error",MDC.get("correlationId"),Map.of()));}
}
