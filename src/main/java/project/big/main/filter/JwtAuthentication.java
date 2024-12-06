package project.big.main.filter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Collections;

public class JwtAuthentication extends UsernamePasswordAuthenticationToken {
    public JwtAuthentication(String principal, Object credentials) {
        super(principal, credentials, Collections.emptyList());
    }
}
