Main task

Please, complete the following task.

The total mark is 7:  5 points for regular homework and 2 points for the extra mile.

1. Transform project from Spring Introduction module into a web application, configure dispatcher servlet. (0.5 point)

2. Implement annotation-based controllers that will delegate to BookingFacade methods. For methods, that accept Entity, just send the list of parameters from the client and assemble the entity in the controller, no need for automatic conversion of request payload to java object. (0.5 point)

3. For methods, that should return a single entity or entity list result (e.g. getUsersByName), implement simple thymeleaf templates for displaying results. No sophisticated markup required, just the fact that you know how to implement the chain:

ModelAndView & Resolver & ThymeleafTemplate & Html page in the browser. (1 point)

4. For the following facade method:

List getBookedTickets(User user, int pageSize, int pageNum);

Implement alternative controller, which will be mapped on header value "accept=application/pdf" and return PDF version of booked tickets list. (0.5 point)

5. Implement batch creation of ticket bookings from XML file. Source file example:

```
<tickets>

    <ticket user="..." event="..." category="..." place="..."/>

    <ticket user="..." event="..." category="..." place="..."/>

    <ticket user="..." event="..." category="..." place="..."/>

</tickets>
```
Add a method public void preloadTickets() to facade that will load this file from some predefined place (or from a location specified in parameter), unmarshal ticket objects using Spring OXM capabilities and update the storage. The whole batch should be performed in a single transaction, using programmatic transaction management. (1 point)

6. Implement custom HandlerExceptionResolver, which in case of controller exception just send a simple text response to the client with a brief description of the error. (0.5 point) 

7. Unit tests, logging, javadocs. (0.5 point)

8. Implement integration tests for Booking service controllers using the MockMVC framework. (0.5 point)

Main task

   Please, complete the following task.

The total mark is 7:  5 points for regular homework and 2 points for the extra mile.

Based on the codebase created during the Spring Web module.

1. Using the Hibernate ORM framework, update existing models. (0.5 point)

2. Add a new model to the application – UserAccount, it should store the amount of prepaid money the user has in the system, which should be used during the booking procedure. Add methods for refilling the account to the BookingFacade class. Add DAO and service objects for the new entities. Add ticketPrice field to Event entity. (0.5 point)

3. Create a database schema for storing application entities. Provide SQL script with schema creation DDL for DBMS of your choice. (0.5 points)

4. Update DAO objects so that they inherit from one of the Spring Data interfaces, for example – CrudRepository. They would store and retrieve application entities from the database. Use transaction management to perform actions in a transaction where it necessary (define the approach to implementing transactions with a mentor). Configure Hibernate for work with DBMS that you choose. (1.5 points)

5. Update ticket booking methods to check and withdraw money from user account according to the ticketPrice for a particular event. (0.5 point)

6. Cover code with unit tests. Code should contain proper logging (0.5 point)

7. Add integration tests for newly implemented scenarios. (0.5 point)