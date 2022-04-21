package com.epam.bookingservice;

import com.epam.bookingservice.dao.storage.DataStorage;
import com.epam.bookingservice.facade.BookingFacade;
import com.epam.bookingservice.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BookingServiceApplication {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookingFacade bookingFacade = context.getBean("bookingFacade", BookingFacade.class);
        DataStorage dataStorage = context.getBean("dataStorage", DataStorage.class);
        System.out.println(bookingFacade.getUserById(3L));
        System.out.println(bookingFacade.getUserByEmail("s@gmail.com"));
        System.out.println(bookingFacade.getUsersByName("a", 1, 2));
//        System.out.println(bookingFacade.createUser(new User("Beatrice", "b@gmail.com")));
        System.out.println("ALL USERS " + dataStorage.getUsers());
//        System.out.println(bookingFacade.updateUser(new User(3L, "NewUser", "b@gmail.com")));
        System.out.println("ALL USERS " + dataStorage.getUsers());
        System.out.println(bookingFacade.deleteUser(1L));
        System.out.println(bookingFacade.getEventById(1L));
        System.out.println(bookingFacade.getBookedTickets(bookingFacade.getUserById(3L), 1, 2));


//        UserService userService = context.getBean("userService", UserService.class);
//        System.out.println(userService.getUserById(3L));
//        System.out.println(userService.getUserByEmail("s@gmail.com"));
//        System.out.println(userService.getUsersByName("a", 1, 2));
//        System.out.println(userService.createUser(new User("Beatrice", "b@gmail.com")));
//        System.out.println(userService.updateUser(new User(1L, "NewUser", "b@gmail.com")));
//        System.out.println(userService.deleteUser(1L));
    }
}
