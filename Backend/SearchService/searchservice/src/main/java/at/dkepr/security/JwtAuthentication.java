package at.dkepr.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthentication implements Authentication {
    private final String token;

    public JwtAuthentication(String token) {
        this.token = token;
    }


    @Override
    public Object getCredentials() {
        return token;
    }


    @Override
    public String getName() {
        return null;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


    @Override
    public Object getDetails() {
        return null;
    }


    @Override
    public Object getPrincipal() {
        return null;
    }


    @Override
    public boolean isAuthenticated() {
        return false;
    }


    @Override
    public void setAuthenticated(boolean arg0) throws IllegalArgumentException {
        
    }
}

