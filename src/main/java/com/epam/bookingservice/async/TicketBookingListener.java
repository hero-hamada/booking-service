//package com.epam.bookingservice.async;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.AllArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//
//@AllArgsConstructor
//@Component
//public class TicketBookingListener {
//
//    private static final Logger LOG = LoggerFactory.getLogger(TicketBookingListener.class);
//    private final ObjectMapper mapper;
//
////    private final BookingFacade bookingFacade;
//
//    public void receiveMessage(String message) {
//        try {
//            AsyncPayload payload = mapper.readValue(message, AsyncPayload.class);
//            if("TICKET".equals(payload.getModel())) {
//                LOG.info("IT WORKS!");;
////                Ticket ticket = bookingFacade.bookTicket(payload.getId())
//            }
//        } catch (JsonMappingException e) {
//            e.printStackTrace();
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
