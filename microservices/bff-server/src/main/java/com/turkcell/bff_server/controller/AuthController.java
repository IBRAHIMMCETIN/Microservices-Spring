package com.turkcell.bff_server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    /**
     * Frontend bu endpoint'i çağırarak oturum açmış kullanıcının bilgisini alır.
     * Token hiçbir zaman response'a eklenmez — sadece claim'ler dönülür.
     */
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> me(@AuthenticationPrincipal OidcUser oidcUser) {
        if (oidcUser == null) {
            return ResponseEntity.status(401).build();
        }

        Map<String, Object> userInfo = Map.of(
                "sub",    oidcUser.getSubject(),
                "email",  oidcUser.getEmail() != null ? oidcUser.getEmail() : "",
                "name",   oidcUser.getFullName() != null ? oidcUser.getFullName() : "",
                "roles",  oidcUser.getClaimAsStringList("realm_access") != null
                              ? oidcUser.getClaimAsStringList("realm_access")
                              : java.util.List.of()
        );

        return ResponseEntity.ok(userInfo);
    }

    /**
     * Oturum durumunu kontrol etmek için kullanılır.
     * 200 → oturum açık, 401 → oturum yok (Spring Security filtresi 401 döner).
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Boolean>> status() {
        return ResponseEntity.ok(Map.of("authenticated", true));
    }
}
