package org.example.repository.impl;

import org.example.db.CreateTables;
import org.example.db.impl.DBConnectionManagerImpl;
import org.example.model.Contact;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ContactRepositoryImplTest {
    ContactRepositoryImpl contactRepository;
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
        contactRepository = new ContactRepositoryImpl(connectionProvider);
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
    void checkContactRep() {
        Contact contact = new Contact();
        contact.setId(UUID.randomUUID());
        contact.setUserId("fba9b929-a765-4e43-bb61-5c3bb47c5084");
        contact.setNotes("Перезвонить");
        contact.setCompany("Aston");
        contact.setName("Егоров Александр Егорович");
        contact.setTelephone("+7 (925) 703-2462");
        contact.setMail("Alex@mail.ru");
        Contact saved = contactRepository.save(contact);// метод save() использует метод getById
        List<Contact> contactList = contactRepository.findAll();
        assertEquals(contact, saved);
        assertEquals(12, contactList.size());// при создании таблиц добавил 11 и выше еще одну сущность = 12
        boolean check = contactRepository.deleteById(contact.getId());
        boolean checkNotId = contactRepository.deleteById(UUID.fromString("e6888207-176f-46c1-b6e3-ef2cb0bce43f"));
        List<Contact> contactListDel = contactRepository.findAll();
        assertTrue(check);
        assertFalse(checkNotId);
        assertEquals(11, contactListDel.size());// при создании таблиц добавил 11, потом добавил одну, а после ее удалил а выше одну сущность удалили = 11

    }

}

