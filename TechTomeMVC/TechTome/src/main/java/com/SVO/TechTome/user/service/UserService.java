package com.SVO.TechTome.user.service;

import com.SVO.TechTome.exception.DomainException;
import com.SVO.TechTome.security.AuthMetaData;
import com.SVO.TechTome.shoppingCart.model.ShoppingCart;
import com.SVO.TechTome.shoppingCart.repository.ShoppingCartRepository;
import com.SVO.TechTome.shoppingCart.service.ShoppingCartService;
import com.SVO.TechTome.subscription.model.Subscription;
import com.SVO.TechTome.subscription.repository.SubscriptionRepository;
import com.SVO.TechTome.subscription.service.SubscriptionService;
import com.SVO.TechTome.user.model.User;
import com.SVO.TechTome.user.model.UserRole;
import com.SVO.TechTome.user.repository.UserRepository;
import com.SVO.TechTome.web.dto.RegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserService implements UserDetailsService {



    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ShoppingCartService shoppingCartService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionService subscriptionService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ShoppingCartService shoppingCartService, ShoppingCartRepository shoppingCartRepository, SubscriptionRepository subscriptionRepository, SubscriptionService subscriptionService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.shoppingCartService = shoppingCartService;
        this.shoppingCartRepository = shoppingCartRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionService = subscriptionService;
    }

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public User register(RegisterRequest registerRequest) {

        Optional<User> optionUser = userRepository.findByEmail(registerRequest.getEmail());
        if (optionUser.isPresent()) {
            throw new DomainException("Username with email [%s] already exist.".formatted(registerRequest.getEmail()));
        }
        User user = userRepository.save(initializeUser(registerRequest));



        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setTotalPrice(BigDecimal.valueOf(0));
        shoppingCart.setOwner(user);
        shoppingCartRepository.save(shoppingCart);
        Subscription subscription = subscriptionService.createDefaultSubscription(user);

        user.setShoppingCart(shoppingCart);





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

        User user = userRepository.findByEmail(username).orElseThrow(() -> new DomainException("User with this email not found."));

        return new AuthMetaData(user.getId(), user.getEmail(), user.getPassword(), user.getRole());
    }

    public User getById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(id.toString()));
    }
}
