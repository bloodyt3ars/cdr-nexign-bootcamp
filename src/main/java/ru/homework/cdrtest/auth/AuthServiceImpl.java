package ru.homework.cdrtest.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;

    @Override
    public void login(LoginDTO dto) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getPhoneNumber(),
                        dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
    }

    @Override
    public void logout(HttpServletRequest request) {
        try {
            request.logout();
        }
        catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
