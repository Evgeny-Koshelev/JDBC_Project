package org.example.repository.impl;

import org.example.db.CreateTables;
import org.example.db.impl.DBConnectionManagerImpl;
import org.example.model.Event;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EventRepositoryImplTest {

    private final DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());
    EventRepositoryImpl eventRepository;
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() {
        DBConnectionManagerImpl connectionProvider = new DBConnectionManagerImpl(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );
        CreateTables createTables = new CreateTables(connectionProvider);
        createTables.create();
        eventRepository = new EventRepositoryImpl(connectionProvider);
    }

    @AfterEach
    void reset() {
        DBConnectionManagerImpl connectionProvider = new DBConnectionManagerImpl(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );
        CreateTables createTables = new CreateTables(connectionProvider);
        createTables.delete();
    }

    @Test
    void getEventsTest() {
        Event event = new Event();
        event.setId(UUID.randomUUID());
        event.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        event.setNotes("get up");
        event.setBeginDate(ZonedDateTime.parse("2022-06-02 09:00:00", formatter));
        event.setIsCompleted(false);
        event.setVacancyId(UUID.fromString("0b705064-e5e4-4eb8-a78f-13aa462db6f2"));
        Event saved = eventRepository.save(event);// метод save() использует метод getById
        List<Event> eventList = eventRepository.findAll();
        assertEquals(event, saved);
        assertEquals(4, eventList.size());// при создании таблиц добавил 3 и выше еще одну сущность = 4
    }

    @Test
    void checkDeleteTest() {
        boolean check = eventRepository.deleteById(UUID.fromString("ac3aed48-71bc-4852-b742-1565b461b94f"));
        boolean checkNotId = eventRepository.deleteById(UUID.fromString("ac5aed55-71bc-4852-b742-1565b461b94f"));
        List<Event> eventList = eventRepository.findAll();
        assertTrue(check);
        assertFalse(checkNotId);
        assertEquals(2, eventList.size());// при создании таблиц добавил 3 а выше одну сущность удалили = 2

    }

}
