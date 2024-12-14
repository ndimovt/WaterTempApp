package io.github.ndimovt.WaterTempApp.service;

import io.github.ndimovt.WaterTempApp.model.User;
import io.github.ndimovt.WaterTempApp.model.dto.LoginUserDto;
import io.github.ndimovt.WaterTempApp.repository.UserRepository;
import io.github.ndimovt.WaterTempApp.roles.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public AuthService(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }
    public User signUp(User user){
        User register = new User();
        register.setId(user.getId());
        register.setUsername(user.getUsername());
        register.setPassword(passwordEncoder.encode(user.getPassword()));
        register.setName(user.getName());
        register.setSurname(user.getSurname());
        register.setRole(Role.USER.name());

        return userRepository.save(register);
    }
    public User authenticate(LoginUserDto loginUserDto){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.getUsername(),
                        loginUserDto.getPassword()
                )
        );
        return userRepository.findByUsername(loginUserDto.getUsername()).orElseThrow();
    }


}
