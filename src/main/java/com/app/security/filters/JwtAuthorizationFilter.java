package com.app.security.filters;

import com.app.security.jwt.JwtUtils;
import com.app.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author luis.buelna
 */

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter{

    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    
    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request, 
            @NotNull HttpServletResponse response, 
            @NotNull FilterChain filterChain) throws ServletException, IOException {
        
        //Obteniendo el header "Authorization" de la peticion
        String tokenHeader = request.getHeader("Authorization");
        
        if(tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.substring(7);
            
            //Si el token es valido, debemos obtener el usuario y demas informacion dentro del token
            if(jwtUtils.isTokenValid(token)){
                String username = jwtUtils.getUsernameFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                
                UsernamePasswordAuthenticationToken authenticationToken = 
                        new UsernamePasswordAuthenticationToken(
                                username, 
                                null, 
                                userDetails.getAuthorities());
                
                //Objeto que contiene la autenticacion propia de la aplicacion
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        
        filterChain.doFilter(request, response);
    }
    
}
