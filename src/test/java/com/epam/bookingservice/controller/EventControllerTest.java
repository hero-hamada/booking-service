package com.epam.bookingservice.controller;

import com.epam.bookingservice.facade.BookingFacade;
import com.epam.bookingservice.model.Event;
import com.epam.bookingservice.model.Ticket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static com.epam.bookingservice.controller.JsonConvertor.asJsonString;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(EventController.class)
class EventControllerTest {

    private static String URL_BOOKINGSERVICE_EVENTS = "/bookingservice/v1/events";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookingFacade bookingFacade;

    @Test
    void getById() throws Exception {
        //given
        Event expectedEvent = new Event();
        expectedEvent.setId(1L);
        expectedEvent.setTitle("Title");
        expectedEvent.setDate(LocalDate.of(2050, Month.APRIL, 4));
        expectedEvent.setTicketPrice(BigDecimal.valueOf(100));
        given(bookingFacade.getEventById(expectedEvent.getId())).willReturn(expectedEvent);

        // when
        ResultActions resultActions = this.mockMvc.perform(
                get(URL_BOOKINGSERVICE_EVENTS + "/{id}", expectedEvent.getId())
        );

        // then
        resultActions.andExpect(status().isOk());
        verify(bookingFacade).getEventById(eq(expectedEvent.getId()));
        String actualUserAsString = resultActions.andReturn().getResponse().getContentAsString();
        assertThat(actualUserAsString).isEqualTo(asJsonString(expectedEvent));
    }

    @Test
    void getEventsByTitle() throws Exception {
        //given
        final String title = "Title";
        final int pageSize = 10;
        final int pageNum = 5;
        Event event = new Event();
        event.setId(1L);
        event.setTitle("Title");
        event.setDate(LocalDate.of(2050, Month.APRIL, 4));
        event.setTicketPrice(BigDecimal.valueOf(100));
        List<Event> expectedEvents = List.of(event);
        given(bookingFacade.getEventsByTitle(title, pageSize, pageNum)).willReturn(expectedEvents);

        // when
        ResultActions resultActions = this.mockMvc.perform(
                get(URL_BOOKINGSERVICE_EVENTS)
                    .param("title", title)
                    .param("pageSize", String.valueOf(pageSize))
                    .param("pageNum", String.valueOf(pageNum)));

        // then
        resultActions.andExpect(status().isOk());
        verify(bookingFacade).getEventsByTitle(eq(title), eq(pageSize), eq(pageNum));
        String actualEventsAsString = resultActions.andReturn().getResponse().getContentAsString();
        assertThat(actualEventsAsString).isEqualTo(asJsonString(expectedEvents));
    }

    @Test
    void getEventsForDay() throws Exception {
        //given
        final LocalDate date = LocalDate.of(2022, Month.AUGUST,8);
        final int pageSize = 10;
        final int pageNum = 5;
        Event event = new Event();
        event.setId(1L);
        event.setTitle("Title");
        event.setDate(date);
        event.setTicketPrice(BigDecimal.valueOf(100));
        List<Event> expectedEvents = List.of(event);
        given(bookingFacade.getEventsForDay(date, pageSize, pageNum)).willReturn(expectedEvents);

        // when
        ResultActions resultActions = this.mockMvc.perform(
                get(URL_BOOKINGSERVICE_EVENTS)
                        .param("day", String.valueOf(date))
                        .param("pageSize", String.valueOf(pageSize))
                        .param("pageNum", String.valueOf(pageNum)));

        // then
        resultActions.andExpect(status().isOk());
        verify(bookingFacade).getEventsForDay(eq(date), eq(pageSize), eq(pageNum));
        String actualEventsAsString = resultActions.andReturn().getResponse().getContentAsString();
        assertThat(actualEventsAsString).isEqualTo(asJsonString(expectedEvents));
    }

    @Test
    void createEvent() throws Exception {
        //given
        Event expectedEvent = new Event();
        expectedEvent.setTitle("Title");
        expectedEvent.setDate(LocalDate.of(2022, Month.AUGUST,8));
        expectedEvent.setTicketPrice(BigDecimal.valueOf(100));
        given(bookingFacade.createEvent(expectedEvent)).willReturn(expectedEvent);

        // when
        ResultActions resultActions = this.mockMvc.perform(
                post(URL_BOOKINGSERVICE_EVENTS)
                        .content(asJsonString(expectedEvent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isCreated());
        verify(bookingFacade).createEvent(eq(expectedEvent));
        String actualEventAsString = resultActions.andReturn().getResponse().getContentAsString();
        assertThat(actualEventAsString).isEqualTo(asJsonString(expectedEvent));
    }

    @Test
    void updateEvent() throws Exception {
        //given
        Event expectedEvent = new Event();
        expectedEvent.setId(1L);
        expectedEvent.setTitle("NewTitle");
        expectedEvent.setDate(LocalDate.of(2022, Month.AUGUST,8));
        expectedEvent.setTicketPrice(BigDecimal.valueOf(100));
        given(bookingFacade.updateEvent(expectedEvent)).willReturn(expectedEvent);

        // when
        ResultActions resultActions = this.mockMvc.perform(
                put(URL_BOOKINGSERVICE_EVENTS + "/{id}", expectedEvent.getId())
                        .content(asJsonString(expectedEvent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk());
        verify(bookingFacade).updateEvent(eq(expectedEvent));
        String actualEventAsString = resultActions.andReturn().getResponse().getContentAsString();
        assertThat(actualEventAsString).isEqualTo(asJsonString(expectedEvent));
    }

    @Test
    void deleteById() throws Exception {
        //given
        final Long eventId = 1L;
        given(bookingFacade.deleteEvent(eventId)).willReturn(true);

        // when
        ResultActions resultActions = this.mockMvc.perform(
                delete(URL_BOOKINGSERVICE_EVENTS + "/{id}", eventId));

        // then
        resultActions.andExpect(status().isOk());
        verify(bookingFacade).deleteEvent(eventId);
    }
}