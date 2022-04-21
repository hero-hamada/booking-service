package com.epam.bookingservice.util;

import com.epam.bookingservice.controller.UserController;
import com.epam.bookingservice.model.Ticket;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class BookedTicketsPdfBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getName());
    private static final String BOOKED_TICKETS_PDF = "bookedTickets.pdf";

    public static void build(List<Ticket> tickets) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(BOOKED_TICKETS_PDF));
            addTicketsToDocument(tickets, document);
        } catch (FileNotFoundException | DocumentException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private static void addTicketsToDocument(List<Ticket> tickets, Document document) throws DocumentException {
        document.open();
        List<Paragraph> paragraphs = tickets.stream()
                .map((ticket) -> new Paragraph(ticket.toString()))
                .toList();
        paragraphs.stream().forEach((paragraph) -> {
            try {
                document.add(paragraph);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        });
        for (Ticket ticket: tickets) {
            Paragraph paragraph = new Paragraph(ticket.toString());
            document.add(paragraph);
        }
        document.close();
    }


}
