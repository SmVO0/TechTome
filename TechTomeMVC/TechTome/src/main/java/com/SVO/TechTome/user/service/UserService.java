package com.SVO.TechTome.user.service;

import com.SVO.TechTome.exception.DomainException;
import com.SVO.TechTome.security.AuthMetaData;
import com.SVO.TechTome.user.model.User;
import com.SVO.TechTome.user.model.UserRole;
import com.SVO.TechTome.user.repository.UserRepository;
import com.SVO.TechTome.web.dto.LoginRequest;
import com.SVO.TechTome.web.dto.RegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserService implements UserDetailsService {



    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User login(LoginRequest loginRequest) {

        Optional<User> optionUser = userRepository.findByEmail(loginRequest.getEmail());
        if (optionUser.isEmpty()) {
            throw new DomainException("Email or password are incorrect.");
        }

        User user = optionUser.get();
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new DomainException("Username or password are incorrect.");
        }

        return user;
    }


    public User register(RegisterRequest registerRequest) {

        Optional<User> optionUser = userRepository.findByEmail(registerRequest.getEmail());
        if (optionUser.isPresent()) {
            throw new DomainException("Username with email [%s] already exist.".formatted(registerRequest.getEmail()));
        }
        User user = initializeUser(registerRequest);

        userRepository.save(user);

        log.info("Successfully create new user account for email [%s] and id [%s]".formatted(user.getEmail(), user.getId()));

        return user;
    }

    private User initializeUser(RegisterRequest registerRequest) {
        return User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(UserRole.USER)
                .email(registerRequest.getEmail())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));

        return new AuthMetaData(user.getId(), user.getEmail(), user.getPassword(), user.getRole());
    }

    public User getById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(id.toString()));
    }
}
