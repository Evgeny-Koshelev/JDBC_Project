package org.example.servlet.mapper;

import org.example.model.Event;
import org.example.servlet.dto.EventDto;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventDtoMapperImplTest {

    private final EventDtoMapperImpl eventDtoMapper = new EventDtoMapperImpl();
    private final DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

    @Test
    void toEntityTest() {
        EventDto eventDto = new EventDto();
        UUID id = UUID.randomUUID();
        eventDto.setId(id);
        eventDto.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        eventDto.setNotes("get up");
        eventDto.setBeginDate(ZonedDateTime.parse("2022-06-02 09:00:00", formatter));
        eventDto.setIsCompleted(false);
        eventDto.setVacancyId(UUID.fromString("0b705064-e5e4-4eb8-a78f-13aa462db6f2"));
        Event event = new Event();
        event.setId(id);
        event.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        event.setNotes("get up");
        event.setBeginDate(ZonedDateTime.parse("2022-06-02 09:00:00", formatter));
        event.setIsCompleted(false);
        event.setVacancyId(UUID.fromString("0b705064-e5e4-4eb8-a78f-13aa462db6f2"));
        Event eventCheck = eventDtoMapper.toEntity(eventDto);
        assertEquals(event, eventCheck);
    }

    @Test
    void toDtoTest() {
        EventDto eventDto = new EventDto();
        UUID id = UUID.randomUUID();
        eventDto.setId(id);
        eventDto.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        eventDto.setNotes("get up");
        eventDto.setBeginDate(ZonedDateTime.parse("2022-06-02 09:00:00", formatter));
        eventDto.setIsCompleted(false);
        eventDto.setVacancyId(UUID.fromString("0b705064-e5e4-4eb8-a78f-13aa462db6f2"));
        Event event = new Event();
        event.setId(id);
        event.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        event.setNotes("get up");
        event.setBeginDate(ZonedDateTime.parse("2022-06-02 09:00:00", formatter));
        event.setIsCompleted(false);
        event.setVacancyId(UUID.fromString("0b705064-e5e4-4eb8-a78f-13aa462db6f2"));
        EventDto eventCheck = eventDtoMapper.toDto(event);
        assertEquals(eventDto, eventCheck);
    }
}
