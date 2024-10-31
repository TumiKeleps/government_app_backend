package za.co.telkom.Authentication_Microservice.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import reactor.core.publisher.Mono;

@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    @Value("${auth.server.url}")
    private String authServerUrl;

    @Value("${realm.name}")
    private String realmName;

    private final WebClient webClient = WebClient.create();
    

    @Override
protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String path = request.getServletPath();
    
    // Define an array of paths to exclude from filtering
    String[] excludedPaths = { "/actuator", "/v3", "/health","/swagger-ui.html" }; // Add more paths as needed
    
    // Check if the request path starts with any of the excluded paths
    for (String excludedPath : excludedPaths) {
        if (path.startsWith(excludedPath)) {
            return true; // Do not filter this request
        }
    }
    
    return false; // Apply filter for all other requests
}

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String apiKey = request.getHeader("x-api-key");

        if (apiKey == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        String url = String.format("%s/realms/%s/check?apiKey=%s", authServerUrl, realmName, apiKey);
        
        Mono<Void> result = webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> {
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            return Mono.empty();
                        })
                .bodyToMono(Void.class);

        result.block(); // Blocking to ensure the filter waits for the WebClient response
        
        if (response.getStatus() == HttpStatus.UNAUTHORIZED.value()) {
            return;
        }

        filterChain.doFilter(request, response);
    }
}