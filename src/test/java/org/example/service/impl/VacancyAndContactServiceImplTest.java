package org.example.service.impl;

import org.example.model.Event;
import org.example.model.VacancyAndContact;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;

class VacancyAndContactServiceImplTest {

    @Test
    void findByIdTest() {
        VacancyAndContactServiceImpl simpleServiceMock = Mockito.mock();
        VacancyAndContact vacancyAndContact = new VacancyAndContact();
        vacancyAndContact.setVacancyId(UUID.randomUUID());
        vacancyAndContact.setContactId(UUID.randomUUID());
        Mockito.when(simpleServiceMock.findById(vacancyAndContact.getVacancyId(), vacancyAndContact.getContactId())).thenReturn(vacancyAndContact);
        Assertions.assertEquals(vacancyAndContact, simpleServiceMock.findById(vacancyAndContact.getVacancyId(), vacancyAndContact.getContactId()));
    }
    @Test
    void saveTest() {
        VacancyAndContactServiceImpl simpleServiceMock = Mockito.mock();
        VacancyAndContact vacancyAndContact = new VacancyAndContact();
        vacancyAndContact.setVacancyId(UUID.randomUUID());
        vacancyAndContact.setContactId(UUID.randomUUID());
        Mockito.when(simpleServiceMock.save(vacancyAndContact)).thenReturn(vacancyAndContact);
        Assertions.assertEquals(vacancyAndContact, simpleServiceMock.save(vacancyAndContact));
        ArgumentCaptor<VacancyAndContact> requestCaptor = ArgumentCaptor.forClass(VacancyAndContact.class);
        Mockito.verify(simpleServiceMock, times(1)).save(requestCaptor.capture());
        VacancyAndContact capturedArgument = requestCaptor.getValue();
        assertNotNull(capturedArgument.getVacancyId());
        assertNotNull(capturedArgument.getContactId());
    }

    @Test
    void deleteTest() {
        VacancyAndContactServiceImpl simpleServiceMock = Mockito.mock();
        VacancyAndContact vacancyAndContact = new VacancyAndContact();
        vacancyAndContact.setVacancyId(UUID.randomUUID());
        vacancyAndContact.setContactId(UUID.randomUUID());
        Mockito.when(simpleServiceMock.delete(vacancyAndContact.getVacancyId(), vacancyAndContact.getContactId())).thenReturn(true);
        Assertions.assertEquals(true, simpleServiceMock.delete(vacancyAndContact.getVacancyId(), vacancyAndContact.getContactId()));
    }

    @Test
    void findAll() {
        VacancyAndContactServiceImpl simpleServiceMock = Mockito.mock();
        List<VacancyAndContact> vacancyAndContactList = new ArrayList<>();
        VacancyAndContact vacancyAndContact = new VacancyAndContact();
        vacancyAndContact.setVacancyId(UUID.randomUUID());
        vacancyAndContact.setContactId(UUID.randomUUID());
        VacancyAndContact vacancyAndContact2 = new VacancyAndContact();
        vacancyAndContact2.setVacancyId(UUID.randomUUID());
        vacancyAndContact2.setContactId(UUID.randomUUID());
        vacancyAndContactList.add(vacancyAndContact);
        vacancyAndContactList.add(vacancyAndContact2);
        Mockito.when(simpleServiceMock.findAll()).thenReturn(vacancyAndContactList);
        Assertions.assertEquals(vacancyAndContactList, simpleServiceMock.findAll());
    }
}
