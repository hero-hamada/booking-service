package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.dao.UserDAO;
import com.epam.bookingservice.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {SpringConfig.class})
//@WebAppConfiguration()
@ExtendWith(MockitoExtension.class)
public class UserDAOTest {

    @Autowired
    private UserDAO underTest;

    @BeforeEach
    public void setUp() {
        underTest.deleteAll();
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    public void canFindUserByEmail() {
        // given
        User expectedUser = new User();
        expectedUser.setName("Rem");
        expectedUser.setEmail("rem@gmail.com");
        underTest.save(expectedUser);

        // when
        final User actualUser = underTest.findByEmail(expectedUser.getEmail());

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
