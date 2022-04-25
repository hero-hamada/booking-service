package com.epam.bookingservice.data.impl;

import com.epam.bookingservice.data.UserRepository;
import com.epam.bookingservice.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase
public class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;
    private User expectedUser;

    @BeforeEach
    public void setUp() {
        underTest.deleteAll();
        User user = new User();
        user.setName("User");
        user.setEmail("user@mail.com");
        expectedUser = underTest.save(user);
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    public void canFindUserByEmail() {
        //given
        final String email = "user@mail.com";
        // when
        final User actualUser = underTest.findByEmail(email);
        // then
        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    public void canFindAllByName() {
        // given
        final String name = "R";
        ArrayList<User> expectedUsers = new ArrayList<>();
        User user1 = new User();
        user1.setName("Ram");
        user1.setEmail("ram@gmail.com");
        User user = new User();
        user.setName("Rem");
        user.setEmail("rem@gmail.com");
        expectedUsers.add(user);
        expectedUsers.add(user1);
        expectedUsers.forEach(u -> underTest.save(u));

        // when
        List<User> actualUsers = underTest.findByNameContaining(name);

        // then
        assertThat(actualUsers).isEqualTo(expectedUsers);
    }
}
