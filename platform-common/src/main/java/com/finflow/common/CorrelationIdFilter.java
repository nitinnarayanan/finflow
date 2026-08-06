package com.finflow.common;
import jakarta.servlet.*; import jakarta.servlet.http.*; import org.slf4j.MDC; import org.springframework.stereotype.Component; import java.io.IOException; import java.util.UUID;
@Component public class CorrelationIdFilter extends OncePerRequestFilterBase { protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)throws ServletException,IOException{String id=req.getHeader("X-Correlation-ID"); if(id==null||id.isBlank())id=UUID.randomUUID().toString(); MDC.put("correlationId",id);res.setHeader("X-Correlation-ID",id);try{chain.doFilter(req,res);}finally{MDC.remove("correlationId");}} }
abstract class OncePerRequestFilterBase extends org.springframework.web.filter.OncePerRequestFilter {}
