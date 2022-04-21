package com.epam.bookingservice.util;

import com.epam.bookingservice.controller.UserController;
import com.epam.bookingservice.model.Tickets;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;

public class JAXBXMLHandler {

    private static final String TICKETS_XML = "tickets.xml";
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getName());

    public static Tickets unmarshall() throws JAXBException {
        Tickets tickets = new Tickets();
        try (InputStream xml = JAXBXMLHandler.class.getClassLoader().getResourceAsStream(TICKETS_XML)) {
            JAXBContext context = JAXBContext.newInstance(Tickets.class);
            tickets = (Tickets) context.createUnmarshaller()
                    .unmarshal(new StreamSource(xml));
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return tickets;
    }
}
