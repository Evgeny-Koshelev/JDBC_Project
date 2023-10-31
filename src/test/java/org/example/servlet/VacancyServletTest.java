package org.example.servlet;

import org.example.model.Contact;
import org.example.model.Event;
import org.example.model.Vacancy;
import org.example.service.impl.VacancyServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class VacancyServletTest {

    private final DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

    @Test
    void doGetTest() throws IOException {
        VacancyServiceImpl simpleServiceMock = Mockito.mock();
        Vacancy vacancy = new Vacancy();
        UUID id = UUID.randomUUID();
        vacancy.setId(id);
        vacancy.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        vacancy.setNotes("Перенести в другой статус");
        vacancy.setCompany("Aston");
        vacancy.setNameVacancy("Java developer");
        vacancy.setSalary(100000);
        vacancy.setStatusId(UUID.fromString("315c79fd-0482-4817-8dcb-83979557204c"));
        Set<Contact> contacts = new HashSet<>();
        Set<Event> events = new HashSet<>();
        Event event = new Event();
        event.setId(UUID.randomUUID());
        event.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        event.setNotes("get up");
        event.setBeginDate(ZonedDateTime.parse("2022-06-02 09:00:00", formatter));
        event.setIsCompleted(false);
        event.setVacancyId(UUID.fromString("0b705064-e5e4-4eb8-a78f-13aa462db6f2"));
        Contact contact = new Contact();
        contact.setId(UUID.randomUUID());
        contact.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        contact.setNotes("Перезвонить");
        contact.setCompany("Aston");
        contact.setName("Егоров Александр Егорович");
        contact.setTelephone("+7 (925) 703-2462");
        contact.setMail("Alex@mail.ru");
        events.add(event);
        contacts.add(contact);
        vacancy.setEvents(events);
        vacancy.setContacts(contacts);

        Mockito.when(simpleServiceMock.findById(vacancy.getId())).thenReturn(vacancy);
        VacancyServlet vacancyServlet = new VacancyServlet(simpleServiceMock);
        vacancyServlet.doGet(new MyReq(vacancy.getId()),new MyResp());
        Mockito.verify(simpleServiceMock).findById(Mockito.any());
        assertTrue(true);
    }


    @Test
    void doPostTest() throws IOException {
        VacancyServiceImpl simpleServiceMock = Mockito.mock();
        Vacancy vacancy = new Vacancy();
        UUID id = UUID.randomUUID();
        vacancy.setId(id);
        vacancy.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        vacancy.setNotes("Перенести в другой статус");
        vacancy.setCompany("Aston");
        vacancy.setNameVacancy("Java developer");
        vacancy.setSalary(100000);
        vacancy.setStatusId(UUID.fromString("315c79fd-0482-4817-8dcb-83979557204c"));
        Mockito.when(simpleServiceMock.save(any())).thenReturn(vacancy);

        VacancyServlet vacancyServlet = new VacancyServlet(simpleServiceMock);
        vacancyServlet.doPost(new MyReq(vacancy.getNameVacancy(),vacancy.getNotes(), vacancy.getStatusId()),new MyResp());
        Mockito.verify(simpleServiceMock).save(Mockito.any());
        assertTrue(true);

    }

    @Test
    void getAllTest() throws IOException {
        VacancyServiceImpl simpleServiceMock = Mockito.mock();
        List<Vacancy> vacancyList = new ArrayList<>();
        Vacancy vacancy = new Vacancy();
        UUID id = UUID.randomUUID();
        vacancy.setId(id);
        vacancy.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        vacancy.setNotes("Перенести в другой статус");
        vacancy.setCompany("Aston");
        vacancy.setNameVacancy("Java developer");
        vacancy.setSalary(100000);
        vacancy.setStatusId(UUID.fromString("315c79fd-0482-4817-8dcb-83979557204c"));
        Set<Contact> contacts = new HashSet<>();
        Set<Event> events = new HashSet<>();
        Event event = new Event();
        event.setId(UUID.randomUUID());
        event.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        event.setNotes("get up");
        event.setBeginDate(ZonedDateTime.parse("2022-06-02 09:00:00", formatter));
        event.setIsCompleted(false);
        event.setVacancyId(UUID.fromString("0b705064-e5e4-4eb8-a78f-13aa462db6f2"));
        Contact contact = new Contact();
        contact.setId(UUID.randomUUID());
        contact.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        contact.setNotes("Перезвонить");
        contact.setCompany("Aston");
        contact.setName("Егоров Александр Егорович");
        contact.setTelephone("+7 (925) 703-2462");
        contact.setMail("Alex@mail.ru");
        events.add(event);
        contacts.add(contact);
        vacancy.setEvents(events);
        vacancy.setContacts(contacts);
        vacancyList.add(vacancy);

        Vacancy vacancy2 = new Vacancy();
        vacancy2.setId(UUID.randomUUID());
        vacancy2.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        vacancy2.setNotes("Перенести в другой статус");
        vacancy2.setCompany("Aston");
        vacancy2.setNameVacancy("Java developer");
        vacancy2.setSalary(100000);
        vacancy2.setStatusId(UUID.fromString("315c79fd-0482-4817-8dcb-83979557204c"));
        Set<Contact> contacts2 = new HashSet<>();
        Set<Event> events2 = new HashSet<>();
        Event event2 = new Event();
        UUID id2 = UUID.randomUUID();
        event2.setId(id2);
        event2.setUserId("fba1b111-a765-4e43-bb61-5c3bb47c1111");
        event2.setNotes("созвон");
        event2.setBeginDate(ZonedDateTime.parse("2022-06-02 09:00:00", formatter));
        event2.setIsCompleted(false);
        event2.setVacancyId(UUID.fromString("0b705064-e5e4-4eb8-a78f-13aa462db6f2"));
        events2.add(event2);
        vacancy2.setEvents(events2);
        Contact contact2 = new Contact();
        contact2.setId(UUID.randomUUID());
        contact2.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        contact2.setNotes("Перезвонить");
        contact2.setCompany("Aston");
        contact2.setName("Егоров Александр Егорович");
        contact2.setTelephone("+7 (925) 703-2462");
        contact2.setMail("Alex@mail.ru");
        contacts2.add(contact2);
        vacancy2.setContacts(contacts2);
        vacancyList.add(vacancy2);
        Mockito.when(simpleServiceMock.findAll()).thenReturn(vacancyList);

        Mockito.when(simpleServiceMock.findAll()).thenReturn(vacancyList);
        VacancyServlet vacancyServlet = new VacancyServlet(simpleServiceMock);
        vacancyServlet.doGet(new MyReq(), new MyResp());
        Mockito.verify(simpleServiceMock).findAll();
        assertTrue(true);
    }

    @Test
    void doDeleteTest() throws IOException {
        VacancyServiceImpl simpleServiceMock = Mockito.mock();
        UUID id = UUID.randomUUID();
        Mockito.when(simpleServiceMock.delete(id)).thenReturn(true);

        VacancyServlet vacancyServlet = new VacancyServlet(simpleServiceMock);
        vacancyServlet.doDelete(new MyReq(id),new MyResp());
        Mockito.verify(simpleServiceMock).delete(Mockito.any());
        assertTrue(true);
    }
}