package com.finflow.common; import java.time.Instant; import java.util.Map;
public record ApiError(Instant timestamp,String code,String message,String correlationId,Map<String,Object> details){}
