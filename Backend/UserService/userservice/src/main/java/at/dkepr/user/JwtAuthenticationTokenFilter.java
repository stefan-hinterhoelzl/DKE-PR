package at.dkepr.user;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    
private String tokenHeader = "Authorization";

@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
    final String requestHeader = request.getHeader(this.tokenHeader);
    if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
        String  authToken = requestHeader.substring(7);
        JwtAuthentication authentication = new JwtAuthentication(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    chain.doFilter(request, response);
}




}
