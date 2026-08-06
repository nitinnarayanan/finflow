package com.finflow.common;
import java.time.Instant; import java.util.Map; import java.util.UUID;
public record EventEnvelope<T>(UUID eventId,String eventType,int schemaVersion,Instant occurredAt,String producer,String correlationId,String traceId,String businessKey,Map<String,String> metadata,T payload){
 public static <T> EventEnvelope<T> of(String type,String producer,String correlation,String key,T payload){return new EventEnvelope<>(UUID.randomUUID(),type,1,Instant.now(),producer,correlation,null,key,Map.of(),payload);} }
