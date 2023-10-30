package org.example.repository.impl;

import org.example.db.CreateTables;
import org.example.db.impl.DBConnectionManagerImpl;
import org.example.model.Vacancy;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VacancyRepositoryImplTest {
    VacancyRepositoryImpl vacancyRepository;
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
        vacancyRepository = new VacancyRepositoryImpl(connectionProvider);
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
    void checkVacancyRep() {
        Vacancy vacancy = new Vacancy();
        vacancy.setId(UUID.randomUUID());
        vacancy.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        vacancy.setNotes("Связаться с представителем");
        vacancy.setCompany("Aston");
        vacancy.setNameVacancy("java developer");
        vacancy.setSalary(100000);
        vacancy.setStatusId(UUID.fromString("315c79fd-0482-4817-8dcb-83979557204c"));
        Vacancy saved = vacancyRepository.save(vacancy);// метод save() использует метод getById
        List<Vacancy> vacancyList = vacancyRepository.findAll();
        assertEquals(vacancy, saved);
        assertEquals(12, vacancyList.size());// при создании таблиц добавил 11 и выше еще одну сущность = 12
        boolean check = vacancyRepository.deleteById(vacancy.getId());
        boolean checkNotId = vacancyRepository.deleteById(UUID.fromString("e6888207-176f-46c1-b6e3-ef2cb0bce43f"));
        List<Vacancy> vacancyListDel = vacancyRepository.findAll();
        assertTrue(check);
        assertFalse(checkNotId);
        assertEquals(11, vacancyListDel.size());// при создании таблиц добавил 11, потом добавил одну, а после ее удалил а выше одну сущность удалили = 11

    }

}