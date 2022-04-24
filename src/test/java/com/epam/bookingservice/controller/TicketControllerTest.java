package com.epam.bookingservice.controller;

import com.epam.bookingservice.facade.BookingFacade;
import com.epam.bookingservice.model.Event;
import com.epam.bookingservice.model.Ticket;
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
import static com.epam.bookingservice.model.Ticket.Category.STANDARD;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(TicketController.class)
class TicketControllerTest {

    private static String URL_BOOKINGSERVICE_TICKETS = "/bookingservice/v1/tickets";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookingFacade bookingFacade;

    @Test
    void createTicket() throws Exception {
        // given
        Ticket ticket = new Ticket();
        ticket.setUserId(1L);
        ticket.setEventId(10L);
        ticket.setPlace(4);
        ticket.setCategory(STANDARD);

        // when
        ResultActions resultActions = this.mockMvc.perform(post(URL_BOOKINGSERVICE_TICKETS)
                .content(asJsonString(ticket))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isCreated());
        verify(bookingFacade).bookTicket(eq(ticket.getUserId()),
                eq(ticket.getEventId()),
                eq(ticket.getPlace()),
                eq(ticket.getCategory()));
    }

    @Test
    void deleteById() throws Exception {
        //given
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setUserId(1L);
        ticket.setEventId(10L);
        ticket.setPlace(4);
        ticket.setCategory(STANDARD);

        // when
        ResultActions resultActions = this.mockMvc.perform(
                delete(URL_BOOKINGSERVICE_TICKETS+"/{id}", ticket.getId())
        );

        // then
        resultActions.andExpect(status().isOk());
        verify(bookingFacade).cancelTicket(eq(ticket.getId()));
    }

    @Test
    void getBookedTicketsByUserId() throws Exception {
        //given
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setUserId(1L);
        ticket.setEventId(10L);
        ticket.setPlace(4);
        ticket.setCategory(STANDARD);

        List<Ticket> tickets = List.of(ticket);
        final User user = new User();
        user.setId(1L);
        final int pageSize = 10;
        final int pageNum = 5;
        given(bookingFacade.getBookedTickets(user, pageSize, pageNum)).willReturn(tickets);
        given(bookingFacade.getUserById(user.getId())).willReturn(user);
        // when
        ResultActions resultActions = this.mockMvc.perform(get(URL_BOOKINGSERVICE_TICKETS)
                .param("userId", String.valueOf(user.getId()))
                .param("pageSize", String.valueOf(pageSize))
                .param("pageNum", String.valueOf(pageNum)));

        // then
        resultActions.andExpect(status().isOk());
        verify(bookingFacade).getBookedTickets(eq(user), eq(pageSize), eq(pageNum));
        String actualTicketsAsString = resultActions.andReturn().getResponse().getContentAsString();
        assertThat(actualTicketsAsString).isEqualTo(asJsonString(tickets));
    }

    @Test
    void getBookedTicketsByEventId() throws Exception {
        //given
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setUserId(1L);
        ticket.setEventId(10L);
        ticket.setPlace(4);
        ticket.setCategory(STANDARD);

        List<Ticket> tickets = List.of(ticket);
        final Event event = new Event() ;
        event.setId(10L);
        final int pageSize = 10;
        final int pageNum = 5;
        given(bookingFacade.getBookedTickets(event, pageSize, pageNum)).willReturn(tickets);
        given(bookingFacade.getEventById(event.getId())).willReturn(event);
        // when
        ResultActions resultActions = this.mockMvc.perform(get(URL_BOOKINGSERVICE_TICKETS)
                .param("eventId", String.valueOf(event.getId()))
                .param("pageSize", String.valueOf(pageSize))
                .param("pageNum", String.valueOf(pageNum)));

        // then
        resultActions.andExpect(status().isOk());
        verify(bookingFacade).getBookedTickets(eq(event), eq(pageSize), eq(pageNum));
        String actualTicketsAsString = resultActions.andReturn().getResponse().getContentAsString();
        assertThat(actualTicketsAsString).isEqualTo(asJsonString(tickets));
    }
}