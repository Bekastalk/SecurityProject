package peaksoft.config.jwtilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import peaksoft.config.JWTservice;
import peaksoft.entity.User;
import peaksoft.repository.UserRepository;

import java.io.IOException;
import java.util.Collections;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Component

public class JWTFilter extends OncePerRequestFilter {
    private final JWTservice jwTservice;
    private final UserRepository userRepository;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");

        if(tokenHeader!=null && tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.substring("Bearer ".length());
            User user=null;
            try {
                user=jwTservice.verifyJwt(token);
            } catch (Exception e) {
                response.sendError(401,"Invalid token");
            }
            User realUser = userRepository.findById(user.getId()).orElseThrow(()-> new NoSuchElementException("Not found"));

            if(realUser==null){
                response.sendError(404, "User not found!!!");
                return;
            }
            SecurityContextHolder.getContext()
                    .setAuthentication(
                            new UsernamePasswordAuthenticationToken(
                            realUser.getEmail(),
                            null,
                                    realUser.getAuthorities())
                    );

        }
        filterChain.doFilter(request, response);
    }
}
