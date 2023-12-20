package ru.homework.cdrtest.auth;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    void login(LoginDTO dto);

    void logout(HttpServletRequest request);
}
