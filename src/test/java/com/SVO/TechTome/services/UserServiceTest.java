package com.SVO.TechTome.services;

import com.SVO.TechTome.constants.ExceptionMessages;
import com.SVO.TechTome.models.ShoppingCart;
import com.SVO.TechTome.models.User;
import com.SVO.TechTome.models.enums.UserRole;
import com.SVO.TechTome.repositories.ShoppingCartRepository;
import com.SVO.TechTome.repositories.UserRepository;
import com.SVO.TechTome.security.AuthMetaData;
import com.SVO.TechTome.web.dto.RegisterRequest;
import com.SVO.TechTome.web.exception.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private ShoppingCartRepository shoppingCartRepository;
    @Mock private SubscriptionService subscriptionService;

    @InjectMocks
    private UserService userService;

    private static final String EMAIL    = "jane@example.com";
    private static final String USERNAME = "janesmith";
    private static final String PASSWORD = "secret99";
    private static final String ENCODED  = "$2a$10$encodedHash";

    private RegisterRequest request;
    private User savedUser;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();

        request = RegisterRequest.builder()
                .username(USERNAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        savedUser = User.builder()
                .id(userId)
                .username(USERNAME)
                .email(EMAIL)
                .password(ENCODED)
                .role(UserRole.USER)
                .build();
    }

    // =============================================
    // register() — happy path
    // =============================================

    @Test
    void register_savesUserWithBCryptPassword() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCODED);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(shoppingCartRepository.save(any(ShoppingCart.class))).thenAnswer(i -> i.getArgument(0));

        userService.register(request);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertThat(captor.getValue().getPassword())
                .isEqualTo(ENCODED)
                .doesNotContain(PASSWORD);
    }

    @Test
    void register_assignsUserRole() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn(ENCODED);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(shoppingCartRepository.save(any(ShoppingCart.class))).thenAnswer(i -> i.getArgument(0));

        userService.register(request);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertThat(captor.getValue().getRole()).isEqualTo(UserRole.USER);
    }

    @Test
    void register_createsShoppingCartWithZeroTotal() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn(ENCODED);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(shoppingCartRepository.save(any(ShoppingCart.class))).thenAnswer(i -> i.getArgument(0));

        userService.register(request);

        ArgumentCaptor<ShoppingCart> captor = ArgumentCaptor.forClass(ShoppingCart.class);
        verify(shoppingCartRepository).save(captor.capture());
        assertThat(captor.getValue().getTotalPrice())
                .isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void register_shoppingCartOwnerIsTheSavedUser() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn(ENCODED);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(shoppingCartRepository.save(any(ShoppingCart.class))).thenAnswer(i -> i.getArgument(0));

        userService.register(request);

        ArgumentCaptor<ShoppingCart> captor = ArgumentCaptor.forClass(ShoppingCart.class);
        verify(shoppingCartRepository).save(captor.capture());
        assertThat(captor.getValue().getOwner()).isEqualTo(savedUser);
    }

    @Test
    void register_createsDefaultSubscriptionForSavedUser() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn(ENCODED);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(shoppingCartRepository.save(any(ShoppingCart.class))).thenAnswer(i -> i.getArgument(0));

        userService.register(request);

        verify(subscriptionService).createDefaultSubscription(savedUser);
    }

    @Test
    void register_returnsTheCreatedUser() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn(ENCODED);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(shoppingCartRepository.save(any(ShoppingCart.class))).thenAnswer(i -> i.getArgument(0));

        User result = userService.register(request);

        assertThat(result).isEqualTo(savedUser);
    }

    @Test
    void register_returnedUserHasShoppingCartAttached() {
        ShoppingCart cart = new ShoppingCart();
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn(ENCODED);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(shoppingCartRepository.save(any(ShoppingCart.class))).thenReturn(cart);

        User result = userService.register(request);

        assertThat(result.getShoppingCart()).isEqualTo(cart);
    }

    @Test
    void register_duplicateEmail_throwsDomainException() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(savedUser));

        assertThatThrownBy(() -> userService.register(request))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining(EMAIL);
    }

    @Test
    void register_duplicateEmail_messageUsesEmailTemplate() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(savedUser));

        assertThatThrownBy(() -> userService.register(request))
                .isInstanceOf(DomainException.class)
                .hasMessage(ExceptionMessages.USER_ALREADY_EXISTS.formatted(EMAIL));
    }

    @Test
    void register_duplicateEmail_neverPersistsUser() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(savedUser));

        assertThatThrownBy(() -> userService.register(request))
                .isInstanceOf(DomainException.class);

        verify(userRepository, never()).save(any());
        verify(shoppingCartRepository, never()).save(any());
        verify(subscriptionService, never()).createDefaultSubscription(any());
    }

    @Test
    void loadUserByUsername_returnsAuthMetaData() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(savedUser));

        UserDetails result = userService.loadUserByUsername(EMAIL);

        assertThat(result).isInstanceOf(AuthMetaData.class);
    }

    @Test
    void loadUserByUsername_authMetaDataHasCorrectFields() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(savedUser));

        AuthMetaData result = (AuthMetaData) userService.loadUserByUsername(EMAIL);

        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.getUsername()).isEqualTo(EMAIL);
        assertThat(result.getPassword()).isEqualTo(ENCODED);
    }

    @Test
    void loadUserByUsername_authorityMatchesUserRole() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(savedUser));

        UserDetails result = userService.loadUserByUsername(EMAIL);

        assertThat(result.getAuthorities())
                .extracting("authority")
                .containsExactly("ROLE_USER");
    }

    @Test
    void loadUserByUsername_unknownEmail_throwsDomainException() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.loadUserByUsername("ghost@example.com"))
                .isInstanceOf(DomainException.class)
                .hasMessage(ExceptionMessages.USER_NOT_FOUND_BY_EMAIL);
    }

    @Test
    void getById_existingId_returnsUser() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(savedUser));

        User result = userService.getById(userId);

        assertThat(result).isEqualTo(savedUser);
    }

    @Test
    void getById_unknownId_throwsUsernameNotFoundException() {
        UUID unknownId = UUID.randomUUID();
        when(userRepository.findById(unknownId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getById(unknownId))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining(unknownId.toString());
    }

    @Test
    void getById_unknownId_messageUsesIdTemplate() {
        UUID unknownId = UUID.randomUUID();
        when(userRepository.findById(unknownId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getById(unknownId))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage(ExceptionMessages.USER_NOT_FOUND_BY_ID.formatted(unknownId));
    }
}