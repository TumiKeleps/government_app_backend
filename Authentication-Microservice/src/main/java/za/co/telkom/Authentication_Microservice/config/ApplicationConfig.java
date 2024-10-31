package za.co.telkom.Authentication_Microservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class ApplicationConfig {

  @Value("${server.port}")     
  private String testKey;
  
}