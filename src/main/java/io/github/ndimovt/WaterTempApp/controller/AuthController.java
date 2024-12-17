package io.github.ndimovt.WaterTempApp.controller;

import io.github.ndimovt.WaterTempApp.model.User;
import io.github.ndimovt.WaterTempApp.model.dto.LoginUserDto;
import io.github.ndimovt.WaterTempApp.response.LoginResponse;
import io.github.ndimovt.WaterTempApp.service.AuthService;
import io.github.ndimovt.WaterTempApp.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtService jwtService;
    private final AuthService authService;

    public AuthController(JwtService jwtService, AuthService authService) {
        this.jwtService = jwtService;
        this.authService = authService;
    }
    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user){
        User signed = authService.signUp(user);
        return ResponseEntity.ok(signed);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> logIn(@RequestBody LoginUserDto loginUserDto){
        User user = authService.authenticate(loginUserDto);
        String token = jwtService.generateToken(user);
        LoginResponse response = new LoginResponse().setToken(token).setExpiresIn(jwtService.getJwtExpiration());
        return ResponseEntity.ok(response);
    }
}
