package com.epam.bookingservice.controller;

import com.epam.bookingservice.facade.BookingFacade;
import com.epam.bookingservice.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.epam.bookingservice.controller.JsonConvertor.asJsonString;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
class UserControllerTest {

    private static String URL_BOOKINGSERVICE_USERS = "/bookingservice/v1/users";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookingFacade bookingFacade;

    @Test
    void getById() throws Exception {
        //given
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setName("User");
        expectedUser.setEmail("user@mail.com");
        given(bookingFacade.getUserById(expectedUser.getId())).willReturn(expectedUser);

        // when
        ResultActions resultActions = this.mockMvc.perform(
                get(URL_BOOKINGSERVICE_USERS + "/{id}", expectedUser.getId())
        );

        // then
        resultActions.andExpect(status().isOk());
        verify(bookingFacade).getUserById(eq(expectedUser.getId()));
        String actualUserAsString = resultActions.andReturn().getResponse().getContentAsString();
        assertThat(actualUserAsString).isEqualTo(asJsonString(expectedUser));
    }

    @Test
    void getByEmail() throws Exception {
        //given
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setName("User");
        expectedUser.setEmail("user@mail.com");
        given(bookingFacade.getUserByEmail(expectedUser.getEmail())).willReturn(expectedUser);

        // when
        ResultActions resultActions = this.mockMvc.perform(
                get(URL_BOOKINGSERVICE_USERS)
                        .param("email", expectedUser.getEmail()));

        // then
        resultActions.andExpect(status().isOk());
        verify(bookingFacade).getUserByEmail(eq(expectedUser.getEmail()));
        String actualUserAsString = resultActions.andReturn().getResponse().getContentAsString();
        assertThat(actualUserAsString).isEqualTo(asJsonString(expectedUser));
    }

    @Test
    void getUsersByName() throws Exception {
        //given
        final String userName = "User";
        final int pageSize = 10;
        final int pageNum = 5;
        User user = new User();
        user.setId(1L);
        user.setName("User");
        user.setEmail("user@mail.com");
        User user1 = new User();
        user1.setId(1L);
        user1.setName("User27");
        user1.setEmail("user@mail.com");
        List<User> expectedUsers = List.of(user, user1);
        given(bookingFacade.getUsersByName(userName, pageSize, pageNum)).willReturn(expectedUsers);

        // when
        ResultActions resultActions = this.mockMvc.perform(get(URL_BOOKINGSERVICE_USERS)
                .param("name",user.getName())
                .param("pageSize", String.valueOf(pageSize))
                .param("pageNum", String.valueOf(pageNum)));

        // then
        resultActions.andExpect(status().isOk());
        verify(bookingFacade).getUsersByName(eq(userName), eq(pageSize), eq(pageNum));
        String actualUsersAsString = resultActions.andReturn().getResponse().getContentAsString();
        assertThat(actualUsersAsString).isEqualTo(asJsonString(expectedUsers));
    }

    @Test
    void createUser() throws Exception {
        //given
        User expectedUser = new User();
        expectedUser.setName("User");
        expectedUser.setEmail("user@mail.com");
        given(bookingFacade.createUser(any())).willReturn(expectedUser);

        // when
        ResultActions resultActions = this.mockMvc.perform(post(URL_BOOKINGSERVICE_USERS)
                .content(asJsonString(expectedUser))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isCreated());
        verify(bookingFacade).createUser(eq(expectedUser));
        String actualUserAsString = resultActions.andReturn().getResponse().getContentAsString();
        assertThat(actualUserAsString).isEqualTo(asJsonString(expectedUser));
    }

    @Test
    void updateUser() throws Exception {
        //given
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setName("User");
        expectedUser.setEmail("user@mail.com");
        given(bookingFacade.updateUser(any())).willReturn(expectedUser);

        // when
        ResultActions resultActions = this.mockMvc.perform(
                put(URL_BOOKINGSERVICE_USERS + "/{id}", expectedUser.getId())
                        .content(asJsonString(expectedUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk());
        verify(bookingFacade).updateUser(eq(expectedUser));
        String actualUserAsString = resultActions.andReturn().getResponse().getContentAsString();
        assertThat(actualUserAsString).isEqualTo(asJsonString(expectedUser));
    }

    @Test
    void deleteById() throws Exception {
        //given
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setName("User");
        expectedUser.setEmail("user@mail.com");
        given(bookingFacade.getUserById(expectedUser.getId())).willReturn(expectedUser);

        // when
        ResultActions resultActions = this.mockMvc.perform(
                delete(URL_BOOKINGSERVICE_USERS + "/{id}", expectedUser.getId())
        );

        // then
        resultActions.andExpect(status().isOk());
        verify(bookingFacade).deleteUser(eq(expectedUser.getId()));
    }
}