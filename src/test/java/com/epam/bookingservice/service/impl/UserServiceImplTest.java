package com.epam.bookingservice.service.impl;

import com.epam.bookingservice.data.UserRepository;
import com.epam.bookingservice.model.User;
import com.epam.bookingservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    private UserService underTest;

    @BeforeEach
    public void setUp() {
        underTest = new UserServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    void getUserById() {
        // given
        User user = new User();
        user.setId(1L);
        user.setName("Rem");
        user.setEmail("rem@gmail.com");
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        // when
        underTest.getUserById(user.getId());
        // then
        verify(userRepository).findById(eq(user.getId()));
    }

    @Test
    void getUserByEmail() {
        // given
        final String email = "rem@gmail.com";
        // when
        User userByEmail = underTest.getUserByEmail(email);
        // then
        verify(userRepository).findByEmail(eq(email));
    }

    @Test
    void getUsersByName() {
        // given
        final String name = "a";
        final int pageSize = 4;
        final int pageNum = 6;
        // when
        List<User> usersByName = underTest.getUsersByName(name, pageSize, pageNum);
        // then
        verify(userRepository).findByNameContaining(eq(name));
    }

    @Test
    void createUser() {
        // given
        User user = new User();
        user.setName("Rem");
        user.setEmail("rem@gmail.com");
        // when
        underTest.createUser(user);
        // then
        verify(userRepository).save(user);
    }

    @Test
    void createUserWillThrowWhenEmailExists() {
        // given
        User user = new User();
        user.setName("Rem");
        user.setEmail("rem@gmail.com");
        given(userRepository.findByEmail(anyString())).willReturn(user);
        // when then
        assertThatThrownBy(() -> underTest.createUser(user))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("Email %s already exists", user.getEmail()));
        verify(userRepository, never()).save(user);
    }

    @Test
    void canUpdateUser() {
        // given
        User user = new User();
        user.setId(1L);
        user.setName("Rem");
        user.setEmail("rem@gmail.com");
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        // when
        underTest.updateUser(user);
        // then
        verify(userRepository).save(user);
    }

    @Test
    void updateUserWillThrowWhenIdNotExists() {
        // given
        User user = new User();
        user.setId(1L);
        user.setName("Rem");
        user.setEmail("rem@gmail.com");
        given(userRepository.findById(anyLong())).willReturn(null);
        // when then
        assertThatThrownBy(() -> underTest.updateUser(user))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("User with id=%s does not exist", user.getId()));
        verify(userRepository, never()).save(user);
    }

    @Test
    void deleteUser() {
        // given
        final Long userId = 1L;
        given(userRepository.findById(anyLong())).willReturn(Optional.of(new User()));
        // when
        underTest.deleteUser(userId);
        // then
        verify(userRepository).deleteById(userId);
    }

    @Test
    void deleteUserWillThrowWhenIdNotExists() {
        // given
        final Long userId = Long.MAX_VALUE;
        given(userRepository.findById(anyLong())).willReturn(null);
        // when
        assertThatThrownBy(() -> underTest.deleteUser(userId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("User with id=%s does not exist", userId));
        // then
        verify(userRepository, never()).deleteById(userId);
    }
}