package org.example.repository.impl;

import org.example.db.CreateTables;
import org.example.db.impl.DBConnectionManagerImpl;
import org.example.model.Status;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StatusRepositoryImplTest {

    StatusRepositoryImpl statusRepository;
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
        statusRepository = new StatusRepositoryImpl(connectionProvider);
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
    void checkStatusRep() {
        Status status = new Status();
        status.setId(UUID.randomUUID());
        status.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        status.setNameStatus("ТЕСТ");
        status.setOrderNum(5);
        Status saved = statusRepository.save(status);// метод save() использует метод getById
        List<Status> vacancyList = statusRepository.findAll();
        assertEquals(status, saved);
        assertEquals(6, vacancyList.size());// при создании таблиц добавил 5 и выше еще одну сущность = 6
        boolean check = statusRepository.deleteById(status.getId());
        boolean checkNotId = statusRepository.deleteById(UUID.fromString("e6888207-176f-46c1-b6e3-ef2cb0bce43f"));
        List<Status> statusListDel = statusRepository.findAll();
        assertTrue(check);
        assertFalse(checkNotId);
        assertEquals(5, statusListDel.size());// при создании таблиц добавил 11, потом добавил одну, а после ее удалил а выше одну сущность удалили = 5

    }
}
