package ru.homework.cdrtest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.homework.cdrtest.dto.LoginDto;
import ru.homework.cdrtest.dto.RegisterDto;
import ru.homework.cdrtest.entity.Abonent;
import ru.homework.cdrtest.entity.Role;
import ru.homework.cdrtest.repository.AbonentRepository;
import ru.homework.cdrtest.repository.RoleRepository;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
@Tag(name = "auth", description = "В этом разделе находятся методы авторизации и идентификации пользователя")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final AbonentRepository abonentRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, AbonentRepository abonentRepository,
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.abonentRepository = abonentRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("login")
    @Operation(summary = "Авторизация пользователя",
            description = "Позволяет авторизоваться пользователю",
            operationId = "authLogin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(type = "Abonent signed success"))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content())})
    public ResponseEntity<String> login(@RequestBody
                                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "В теле запроса обязательно должен быть логин и пароль")
                                        LoginDto loginDto) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return new ResponseEntity<>("Abonent signed success", HttpStatus.OK);

    }

    @PostMapping("register")
    @Operation(summary = "Регистрация пользователя",
            description = "Позволяет зарегистрировать пользователя",
            operationId = "authRegister")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(type = "Abonent registered success"))),
            @ApiResponse(responseCode = "400", description = "username is already taken", content = @Content()),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content())})
    public ResponseEntity<String> register(@RequestBody
                                           @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "В теле запроса обязательно должно быть имя, фамилия, логин и пароль")
                                           RegisterDto registerDto) {
        if (abonentRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("username is already taken", HttpStatus.BAD_REQUEST);
        }
        Abonent abonent = new Abonent();
        abonent.setFirstName(registerDto.getFirstName());
        abonent.setLastName(registerDto.getLastName());
        abonent.setUsername(registerDto.getUsername());
        abonent.setPassword(passwordEncoder.encode((registerDto.getPassword())));

        Role role = roleRepository.findByName("ROLE_USER").get();
        abonent.setRoles(Collections.singleton(role));

        abonentRepository.save(abonent);
        return new ResponseEntity<>("Abonent registered success", HttpStatus.OK);
    }

    @PostMapping("logout")
    @Operation(summary = "Выход пользователя из системы",
            description = "Позволяет пользователю разлогиниться",
            operationId = "authLogout")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(type = "Logged out successfully"))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content())})
    public ResponseEntity<String> logout(HttpServletRequest request) {
        try {
            request.logout();
            return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

}
