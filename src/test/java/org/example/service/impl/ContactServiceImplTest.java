package org.example.service.impl;

import org.example.model.Contact;
import org.example.model.Event;
import org.example.model.Vacancy;
import org.example.repository.impl.ContactRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

class ContactServiceImplTest {

    private final DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

    @Test
    void findByIdTest() {
        ContactRepositoryImpl simpleServiceMock = Mockito.mock();
        Contact contact = new Contact();
        contact.setId(UUID.randomUUID());
        contact.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        contact.setNotes("Перезвонить");
        contact.setCompany("Aston");
        contact.setName("Егоров Александр Егорович");
        contact.setTelephone("+7 (925) 703-2462");
        contact.setMail("Alex@mail.ru");
        Set<Vacancy> vacancies = new HashSet<>();
        Vacancy vacancy = new Vacancy();
        vacancy.setId(UUID.randomUUID());
        vacancy.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        vacancy.setNotes("Перенести в другой статус");
        vacancy.setCompany("Aston");
        vacancy.setNameVacancy("Java developer");
        vacancy.setSalary(100000);
        vacancy.setStatusId(UUID.fromString("315c79fd-0482-4817-8dcb-83979557204c"));
        Set<Event> events = new HashSet<>();
        Event event = new Event();
        event.setId(UUID.randomUUID());
        event.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        event.setNotes("get up");
        event.setBeginDate(ZonedDateTime.parse("2022-06-02 09:00:00", formatter));
        event.setIsCompleted(false);
        event.setVacancyId(UUID.fromString("0b705064-e5e4-4eb8-a78f-13aa462db6f2"));
        events.add(event);
        vacancy.setEvents(events);
        vacancies.add(vacancy);
        contact.setVacancies(vacancies);

        Mockito.when(simpleServiceMock.findById(contact.getId())).thenReturn(contact);
        Contact check = simpleServiceMock.findById(contact.getId());
        ContactServiceImpl contactService = new ContactServiceImpl(simpleServiceMock);
        assertEquals(check, contactService.findById(contact.getId()));
    }
    @Test
    void saveTest() {
        ContactRepositoryImpl simpleServiceMock = Mockito.mock();
        Contact contact = new Contact();
        contact.setId(UUID.randomUUID());
        contact.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        contact.setNotes("Перезвонить");
        contact.setCompany("Aston");
        contact.setName("Егоров Александр Егорович");
        contact.setTelephone("+7 (925) 703-2462");
        contact.setMail("Alex@mail.ru");
        Mockito.when(simpleServiceMock.save(contact)).thenReturn(contact);

        Contact check = simpleServiceMock.save(contact);
        ContactServiceImpl contactService = new ContactServiceImpl(simpleServiceMock);
        assertEquals(check, contactService.save(contact));

        ArgumentCaptor<Contact> requestCaptor = ArgumentCaptor.forClass(Contact.class);
        Mockito.verify(simpleServiceMock, times(2)).save(requestCaptor.capture());
        Contact capturedArgument = requestCaptor.getValue();
        assertNotNull(capturedArgument.getId());
        assertNotNull(capturedArgument.getUserId());
        assertNotNull(capturedArgument.getName());

    }

    @Test
    void deleteTest() {
        ContactRepositoryImpl simpleServiceMock = Mockito.mock();
        UUID id = UUID.randomUUID();
        Mockito.when(simpleServiceMock.deleteById(id)).thenReturn(true);

        boolean check = simpleServiceMock.deleteById(id);
        ContactServiceImpl contactService = new ContactServiceImpl(simpleServiceMock);
        assertEquals(check, contactService.delete(id));
    }

    @Test
    void findAll() {
        ContactRepositoryImpl simpleServiceMock = Mockito.mock();
        List<Contact> contactList = new ArrayList<>();

        Contact contact = new Contact();
        contact.setId(UUID.randomUUID());
        contact.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        contact.setNotes("Перезвонить");
        contact.setCompany("Aston");
        contact.setName("Егоров Александр Егорович");
        contact.setTelephone("+7 (925) 703-2462");
        contact.setMail("Alex@mail.ru");
        Set<Vacancy> vacancies = new HashSet<>();

        Vacancy vacancy = new Vacancy();
        vacancy.setId(UUID.randomUUID());
        vacancy.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        vacancy.setNotes("Перенести в другой статус");
        vacancy.setCompany("Aston");
        vacancy.setNameVacancy("Java developer");
        vacancy.setSalary(100000);
        vacancy.setStatusId(UUID.fromString("315c79fd-0482-4817-8dcb-83979557204c"));
        Set<Event> events = new HashSet<>();

        Event event = new Event();
        event.setId(UUID.randomUUID());
        event.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        event.setNotes("get up");
        event.setBeginDate(ZonedDateTime.parse("2022-06-02 09:00:00", formatter));
        event.setIsCompleted(false);
        event.setVacancyId(UUID.fromString("0b705064-e5e4-4eb8-a78f-13aa462db6f2"));
        events.add(event);
        vacancy.setEvents(events);
        vacancies.add(vacancy);
        contact.setVacancies(vacancies);
        contactList.add(contact);



        Contact contact2 = new Contact();
        contact2.setId(UUID.randomUUID());
        contact2.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        contact2.setNotes("Перезвонить");
        contact2.setCompany("Aston");
        contact2.setName("Шаров Виктор Егорович");
        contact2.setTelephone("+7 (925) 703-2462");
        contact2.setMail("Vik@mail.ru");
        Set<Vacancy> vacancies2 = new HashSet<>();

        Vacancy vacancy2 = new Vacancy();
        vacancy2.setId(UUID.randomUUID());
        vacancy2.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        vacancy2.setNotes("Перенести в другой статус");
        vacancy2.setCompany("Dsr");
        vacancy2.setNameVacancy("Java developer");
        vacancy2.setSalary(100000);
        vacancy2.setStatusId(UUID.fromString("315c79fd-0482-4817-8dcb-83979557204c"));
        Set<Event> events2 = new HashSet<>();

        Event event2 = new Event();
        event2.setId(UUID.randomUUID());
        event2.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        event2.setNotes("get up");
        event2.setBeginDate(ZonedDateTime.parse("2022-06-02 09:00:00", formatter));
        event2.setIsCompleted(false);
        event2.setVacancyId(UUID.fromString("0b705064-e5e4-4eb8-a78f-13aa462db6f2"));
        events2.add(event2);
        vacancy2.setEvents(events2);
        vacancies2.add(vacancy2);
        contact2.setVacancies(vacancies2);
        contactList.add(contact2);

        Mockito.when(simpleServiceMock.findAll()).thenReturn(contactList);
        List<Contact> check = simpleServiceMock.findAll();
        ContactServiceImpl contactService = new ContactServiceImpl(simpleServiceMock);
        assertEquals(check, contactService.findAll());
    }
}
