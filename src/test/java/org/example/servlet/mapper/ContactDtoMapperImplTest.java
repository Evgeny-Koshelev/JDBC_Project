package org.example.servlet.mapper;

import org.example.model.Contact;
import org.example.model.Event;
import org.example.model.Vacancy;
import org.example.servlet.dto.ContactDto;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContactDtoMapperImplTest {

    private final ContactDtoMapperImpl contactDtoMapper = new ContactDtoMapperImpl();
    private final DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());


    @Test
    void toEntityTest() {
        Contact contact = new Contact();
        contact.setId(UUID.randomUUID());
        contact.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        contact.setNotes("Перезвонить");
        contact.setCompany("Aston");
        contact.setName("Егоров Александр Егорович");
        contact.setTelephone("+7 (925) 703-2462");
        contact.setMail("Alex@mail.ru");
        ContactDto contactdto = new ContactDto();
        contactdto.setId(contact.getId());
        contactdto.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        contactdto.setNotes("Перезвонить");
        contactdto.setCompany("Aston");
        contactdto.setName("Егоров Александр Егорович");
        contactdto.setTelephone("+7 (925) 703-2462");
        contactdto.setMail("Alex@mail.ru");
        Contact check = contactDtoMapper.toEntity(contactdto);
        assertEquals(contact, check);
    }

    @Test
    void toDtoTest() {
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
        ContactDto contactDto = new ContactDto();
        contactDto.setId(contact.getId());
        contactDto.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        contactDto.setNotes("Перезвонить");
        contactDto.setCompany("Aston");
        contactDto.setName("Егоров Александр Егорович");
        contactDto.setTelephone("+7 (925) 703-2462");
        contactDto.setMail("Alex@mail.ru");
        contactDto.setVacancies(vacancies);
        ContactDto check = contactDtoMapper.toDto(contact);
        assertEquals(contactDto, check);
    }
}
