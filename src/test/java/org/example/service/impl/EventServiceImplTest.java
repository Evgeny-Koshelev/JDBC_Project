package org.example.service.impl;

import org.example.model.Event;
import org.example.repository.impl.EventRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;


class EventServiceImplTest {
    private final DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

    @Test
    void findByIdTest() {
        EventRepositoryImpl simpleServiceMock = Mockito.mock();
        Event event = new Event();
        UUID id = UUID.randomUUID();
        event.setId(id);
        event.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        event.setNotes("get up");
        event.setBeginDate(ZonedDateTime.parse("2022-06-02 09:00:00", formatter));
        event.setIsCompleted(false);
        event.setVacancyId(UUID.fromString("0b705064-e5e4-4eb8-a78f-13aa462db6f2"));
        Mockito.when(simpleServiceMock.findById(id)).thenReturn(event);
        Event check = simpleServiceMock.findById(id);
        EventServiceImpl eventService = new EventServiceImpl(simpleServiceMock);
        assertEquals(check, eventService.findById(id));
    }
    @Test
    void saveTest() {
        EventRepositoryImpl simpleServiceMock = Mockito.mock();
        Event event = new Event();
        UUID id = UUID.randomUUID();
        event.setId(id);
        event.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        event.setNotes("get up");
        event.setBeginDate(ZonedDateTime.parse("2022-06-02 09:00:00", formatter));
        event.setIsCompleted(false);
        event.setVacancyId(UUID.fromString("0b705064-e5e4-4eb8-a78f-13aa462db6f2"));
        Mockito.when(simpleServiceMock.save(event)).thenReturn(event);
        Event check = simpleServiceMock.save(event);
        EventServiceImpl eventService = new EventServiceImpl(simpleServiceMock);
        assertEquals(check, eventService.save(event));
        ArgumentCaptor<Event> requestCaptor = ArgumentCaptor.forClass(Event.class);
        Mockito.verify(simpleServiceMock, times(2)).save(requestCaptor.capture());
        Event capturedArgument = requestCaptor.getValue();
        assertNotNull(capturedArgument.getId());
        assertNotNull(capturedArgument.getUserId());
        assertNotNull(capturedArgument.getBeginDate());
        assertNotNull(capturedArgument.getVacancyId());
        assertNotNull(capturedArgument.getNotes());
        assertFalse(capturedArgument.getIsCompleted());

    }

    @Test
    void deleteTest() {
        EventRepositoryImpl simpleServiceMock = Mockito.mock();
        UUID id = UUID.randomUUID();
        Mockito.when(simpleServiceMock.deleteById(id)).thenReturn(true);
        boolean check = simpleServiceMock.deleteById(id);
        EventServiceImpl eventService = new EventServiceImpl(simpleServiceMock);
        assertEquals(check, eventService.delete(id));
    }

    @Test
    void findAll() {
        EventRepositoryImpl simpleServiceMock = Mockito.mock();
        List<Event> eventList = new ArrayList<>();
        Event event = new Event();
        UUID id = UUID.randomUUID();
        event.setId(id);
        event.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        event.setNotes("созвон");
        event.setBeginDate(ZonedDateTime.parse("2022-06-02 09:00:00", formatter));
        event.setIsCompleted(false);
        event.setVacancyId(UUID.fromString("0b705064-e5e4-4eb8-a78f-13aa462db6f2"));
        eventList.add(event);

        Event event2 = new Event();
        UUID id2 = UUID.randomUUID();
        event2.setId(id2);
        event2.setUserId("fba1b111-a765-4e43-bb61-5c3bb47c1111");
        event2.setNotes("созвон");
        event2.setBeginDate(ZonedDateTime.parse("2022-06-02 09:00:00", formatter));
        event2.setIsCompleted(false);
        event2.setVacancyId(UUID.fromString("0b705064-e5e4-4eb8-a78f-13aa462db6f2"));
        eventList.add(event2);
        Mockito.when(simpleServiceMock.findAll()).thenReturn(eventList);

        List<Event> check = simpleServiceMock.findAll();
        EventServiceImpl eventService = new EventServiceImpl(simpleServiceMock);
        assertEquals(check, eventService.findAll());
    }
}
