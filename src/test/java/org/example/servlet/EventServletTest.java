package org.example.servlet;

import org.example.model.Event;
import org.example.service.impl.EventServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

class EventServletTest {

    private final DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

    @Test
    void doGetTest() throws IOException {
        EventServiceImpl simpleServiceMock = Mockito.mock();
        Event event = new Event();
        UUID id = UUID.randomUUID();
        event.setId(id);
        event.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        event.setNotes("get up");
        event.setBeginDate(ZonedDateTime.parse("2022-06-02 09:00:00", formatter));
        event.setIsCompleted(false);
        event.setVacancyId(UUID.fromString("0b705064-e5e4-4eb8-a78f-13aa462db6f2"));

        Mockito.when(simpleServiceMock.findById(event.getId())).thenReturn(event);
        EventServlet eventServlet = new EventServlet(simpleServiceMock);
        eventServlet.doGet(new MyReq(event.getId()),new MyResp());
        Mockito.verify(simpleServiceMock).findById(Mockito.any());
        assertTrue(true);

    }
    @Test
    void doPostTest() throws IOException {
        EventServiceImpl simpleServiceMock = Mockito.mock();
        Event event = new Event();
        UUID id = UUID.randomUUID();
        event.setId(id);
        event.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        event.setNotes("get up");
        event.setBeginDate(ZonedDateTime.parse("2022-06-02 09:00:00", formatter));
        event.setIsCompleted(false);
        event.setVacancyId(UUID.fromString("0b705064-e5e4-4eb8-a78f-13aa462db6f2"));
        Mockito.when(simpleServiceMock.save(any())).thenReturn(event);

        EventServlet eventServlet = new EventServlet(simpleServiceMock);
        eventServlet.doPost(new MyReq(event.getNotes(),"2022-06-02 09:00:00", event.getVacancyId()) ,new MyResp());
        Mockito.verify(simpleServiceMock).save(Mockito.any());
        assertTrue(true);
    }

    @Test
    void getAllTest() throws IOException {
        EventServiceImpl simpleServiceMock = Mockito.mock();
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
        EventServlet eventServlet = new EventServlet(simpleServiceMock);
        eventServlet.doGet(new MyReq(), new MyResp());
        Mockito.verify(simpleServiceMock).findAll();
        assertTrue(true);
    }

    @Test
    void doDeleteTest() throws IOException {
        EventServiceImpl simpleServiceMock = Mockito.mock();
        UUID id = UUID.randomUUID();
        Mockito.when(simpleServiceMock.delete(id)).thenReturn(true);

        EventServlet eventServlet = new EventServlet(simpleServiceMock);
        eventServlet.doDelete(new MyReq(id),new MyResp());
        Mockito.verify(simpleServiceMock).delete(Mockito.any());
        assertTrue(true);
    }
}
