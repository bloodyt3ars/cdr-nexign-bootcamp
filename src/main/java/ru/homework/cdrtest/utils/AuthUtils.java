package ru.homework.cdrtest.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthUtils {

    public static Boolean isAdmin() {
        return SecurityContextHolder.
                getContext()
                .getAuthentication()
                .getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }

    public static Boolean isAuthenticated(){
        return SecurityContextHolder.
                getContext().
                getAuthentication().
                isAuthenticated();
    }
}
