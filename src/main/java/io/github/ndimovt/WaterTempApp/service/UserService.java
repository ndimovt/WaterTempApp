package io.github.ndimovt.WaterTempApp.service;

import io.github.ndimovt.WaterTempApp.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
