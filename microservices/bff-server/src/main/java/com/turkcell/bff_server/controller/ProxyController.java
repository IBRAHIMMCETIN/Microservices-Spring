package com.turkcell.bff_server.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class ProxyController {

    private final RestClient restClient;

    public ProxyController(@Value("${bff.gateway-url}") String gatewayUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(gatewayUrl)
                .build();
    }

    /**
     * Frontend'den gelen /api/** isteklerini Gateway'e proxy'ler.
     * Session'daki access token otomatik olarak Bearer header'a eklenir.
     * Token hiçbir zaman frontend'e döndürülmez.
     */
    @RequestMapping("/api/**")
    public ResponseEntity<byte[]> proxy(
            HttpServletRequest request,
            @RequestBody(required = false) byte[] body,
            @RegisteredOAuth2AuthorizedClient("keycloak") OAuth2AuthorizedClient authorizedClient
    ) {
        String accessToken = authorizedClient.getAccessToken().getTokenValue();

        String uri = request.getRequestURI();
        String query = request.getQueryString();
        String fullUri = query != null ? uri + "?" + query : uri;

        HttpMethod method = HttpMethod.valueOf(request.getMethod());

        var spec = restClient.method(method)
                .uri(fullUri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

        // Content-Type'ı upstream'den taşı (POST/PUT/PATCH için gerekli)
        String contentType = request.getContentType();
        if (contentType != null) {
            spec = spec.contentType(MediaType.parseMediaType(contentType));
        }

        // Accept header'ı ilet
        String accept = request.getHeader(HttpHeaders.ACCEPT);
        if (accept != null) {
            spec = spec.header(HttpHeaders.ACCEPT, accept);
        }

        if (body != null && body.length > 0) {
            return spec.body(body).retrieve().toEntity(byte[].class);
        }

        return spec.retrieve().toEntity(byte[].class);
    }
}
