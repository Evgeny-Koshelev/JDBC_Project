package org.example.service.impl;

import org.example.model.Contact;
import org.example.model.Event;
import org.example.model.Status;
import org.example.model.Vacancy;
import org.example.repository.impl.StatusRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

class StatusServiceImplTest {

    private final DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

    @Test
    void findByIdTest() {
        StatusRepositoryImpl simpleServiceMock = Mockito.mock();
        Status status = new Status();
        status.setId(UUID.randomUUID());
        status.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        status.setNameStatus("ТЕСТ");
        status.setOrderNum(5);
        Set<Vacancy> vacancies = new HashSet<>();
        Vacancy vacancy = new Vacancy();
        vacancy.setId(UUID.randomUUID());
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
        vacancies.add(vacancy);
        status.setVacancies(vacancies);

        Mockito.when(simpleServiceMock.findById(status.getId())).thenReturn(status);
        Status check = simpleServiceMock.findById(status.getId());
        StatusServiceImpl statusService = new StatusServiceImpl(simpleServiceMock);
        assertEquals(check, statusService.findById(status.getId()));
    }
    @Test
    void saveTest() {
        StatusRepositoryImpl simpleServiceMock = Mockito.mock();
        Status status = new Status();
        status.setId(UUID.randomUUID());
        status.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        status.setNameStatus("ТЕСТ");
        status.setOrderNum(5);

        Mockito.when(simpleServiceMock.save(status)).thenReturn(status);
        Status check = simpleServiceMock.save(status);
        StatusServiceImpl statusService = new StatusServiceImpl(simpleServiceMock);
        assertEquals(check, statusService.save(status));

        ArgumentCaptor<Status> requestCaptor = ArgumentCaptor.forClass(Status.class);
        Mockito.verify(simpleServiceMock, times(2)).save(requestCaptor.capture());
        Status capturedArgument = requestCaptor.getValue();
        assertNotNull(capturedArgument.getId());
        assertNotNull(capturedArgument.getUserId());
        assertNotNull(capturedArgument.getNameStatus());
        assertNotNull(capturedArgument.getOrderNum());

    }

    @Test
    void deleteTest() {
        StatusRepositoryImpl simpleServiceMock = Mockito.mock();
        UUID id = UUID.randomUUID();
        Mockito.when(simpleServiceMock.deleteById(id)).thenReturn(true);

        boolean check = simpleServiceMock.deleteById(id);
        StatusServiceImpl statusService = new StatusServiceImpl(simpleServiceMock);
        assertEquals(check, statusService.delete(id));
    }

    @Test
    void findAll() {
        StatusRepositoryImpl simpleServiceMock = Mockito.mock();
        List<Status> statusList = new ArrayList<>();
        Status status = new Status();
        status.setId(UUID.randomUUID());
        status.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        status.setNameStatus("ТЕСТ");
        status.setOrderNum(5);

        Set<Vacancy> vacancyList = new HashSet<>();
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

        status.setVacancies(vacancyList);
        statusList.add(status);
        Mockito.when(simpleServiceMock.findAll()).thenReturn(statusList);

        List<Status> check = simpleServiceMock.findAll();
        StatusServiceImpl statusService = new StatusServiceImpl(simpleServiceMock);
        assertEquals(check, statusService.findAll());
    }
}
