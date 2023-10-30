package org.example.repository.impl;

import org.example.db.CreateTables;
import org.example.db.impl.DBConnectionManagerImpl;
import org.example.model.VacancyAndContact;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


class VacancyAndContactRepositoryImplTest {
    VacancyAndContactRepositoryImpl vacancyAndContactRepository;
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
        vacancyAndContactRepository = new VacancyAndContactRepositoryImpl(connectionProvider);
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
    void getVacanciesAndContactsTest() {
        VacancyAndContact vacancyAndContact = new VacancyAndContact();
        vacancyAndContact.setVacancyId(UUID.fromString("fd77988f-3eda-46a5-84bb-eac018c97b15"));
        vacancyAndContact.setContactId(UUID.fromString("1b194e03-3333-4543-a333-b9a12876b1a7"));
        VacancyAndContact saved = vacancyAndContactRepository.save(vacancyAndContact);// метод save() использует метод getById
        List<VacancyAndContact> vacancyAndContactList = vacancyAndContactRepository.findAll();
        assertEquals(vacancyAndContact, saved);
        assertEquals(14, vacancyAndContactList.size());// при создании таблиц добавил 13 и выше еще одну сущность = 14
    }

    @Test
    void checkDeleteTest() {
        boolean check = vacancyAndContactRepository.deleteById(UUID.fromString("39b4f92a-ea5a-414f-ba68-e5518aee3f1d"), UUID.fromString("1b194e03-3333-4543-a333-b9a12876b1a7"));
        boolean checkNotId = vacancyAndContactRepository.deleteById(UUID.fromString("44b4f92a-ea5a-414f-ba68-e5518aee3f1d"), UUID.fromString("5b194e03-3333-4543-a333-b9a12876b1a7"));
        List<VacancyAndContact> vacancyAndContactList = vacancyAndContactRepository.findAll();
        assertTrue(check);
        assertFalse(checkNotId);
        assertEquals(12, vacancyAndContactList.size());// при создании таблиц добавил 13 а выше одну сущность удалили = 12

    }
}
