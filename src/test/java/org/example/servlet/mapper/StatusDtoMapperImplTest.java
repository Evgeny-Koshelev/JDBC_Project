package org.example.servlet.mapper;

import org.example.model.Contact;
import org.example.model.Event;
import org.example.model.Status;
import org.example.model.Vacancy;
import org.example.servlet.dto.StatusDto;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatusDtoMapperImplTest {

    private final StatusDtoMapperImpl statusDtoMapper = new StatusDtoMapperImpl();
    private final DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());


    @Test
    void toEntityTest() {
        Status status = new Status();
        status.setId(UUID.randomUUID());
        status.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        status.setNameStatus("ТЕСТ");
        status.setOrderNum(5);
        StatusDto statusDto = new StatusDto();
        statusDto.setId(status.getId());
        statusDto.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        statusDto.setNameStatus("ТЕСТ");
        statusDto.setOrderNum(5);

        Status check = statusDtoMapper.toEntity(statusDto);
        assertEquals(status, check);
    }

    @Test
    void toDtoTest() {
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

        StatusDto statusDto = new StatusDto();
        statusDto.setId(status.getId());
        statusDto.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        statusDto.setNameStatus("ТЕСТ");
        statusDto.setOrderNum(5);
        statusDto.setVacancies(vacancies);


        StatusDto check = statusDtoMapper.toDto(status);
        assertEquals(statusDto, check);
    }
}
