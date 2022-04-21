package com.epam.bookingservice.controller;

import com.epam.bookingservice.config.SpringConfig;
import com.epam.bookingservice.facade.BookingFacade;
import com.epam.bookingservice.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
@WebAppConfiguration()
@Import(BindingResult.class)
class UserControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private BookingFacade bookingFacade;
    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private BindingResult bindingResult;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesUserController() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("userController"));
    }

    @Test
    public void givenUsersURI_whenMockMVC_thenReturnsIndex() throws Exception {
        this.mockMvc.perform(get("/users")).andDo(print())
                .andExpect(view().name("/users/index"));
    }

    @Test
    void getByIdShouldReturnUsersView() throws Exception {
        // given
        final Long userId = 1L;
//        final User expectedUser = new User(1L, "Rem", "r@gmail.com");

        // when
        ResultActions resultActions = this.mockMvc.perform(get("/users/{id}", userId))
                .andDo(print());
        // then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(view().name("/users/view"));
//        resultActions.andExpect(model().attribute("user", equalTo(expectedUser)));
    }

    @Test
    void getByEmailShouldAddAttributeUser() throws Exception {
        // given
        final String email = "r@gmail.com";
//        final User expectedUser = new User(1L, "Rem", "r@gmail.com");

        // when
        ResultActions resultActions = this.mockMvc.perform(get("/users/search/items").
                        param("email", email)).andDo(print());

        // then
        resultActions.andExpect(status().isOk());
//        resultActions.andExpect(model().attribute("user", equalTo(expectedUser)));
        resultActions.andExpect(view().name("/users/view"));
    }

    @Test
    void getUsersByName() throws Exception {
        // given
        final String name = "Rem";
        final int pageSize = 2;
        final int pageNum = 3;

//        final List<User> expectedUsers = List.of(new User(1L, "Rem", "r@gmail.com"));

        // when
        ResultActions resultActions = this.mockMvc.perform(get("/search")
                .param("name", name)
                .param("pageSize", String.valueOf(pageSize))
                .param("pageNum", String.valueOf(pageNum))
                )
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk());
//        resultActions.andExpect(model().attribute("user", equalTo(expectedUsers)));
        resultActions.andExpect(view().name("/users/index"));
    }

    @Test
    void addUser() throws Exception {
        // when
        ResultActions resultActions = this.mockMvc.perform(get("/add")).andDo(print());

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(view().name("users/add"));
    }

    @Test
    void createUser() throws Exception {
        // given
//        final User expectedUser = new User(10L, "New", "email@gmail.com");
        bindingResult = mock(BindingResult.class);
//        // when
//        when(bindingResult.hasErrors()).thenReturn(false);
//        String userToString = asJsonString(expectedUser);
//        ResultActions resultActions = this.mockMvc.perform(post("/users/")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(expectedUser))
//                .accept(MediaType.APPLICATION_JSON));

        // then
//        resultActions.andExpect(status().isOk());
//        resultActions.andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void editUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteById() {
    }
}