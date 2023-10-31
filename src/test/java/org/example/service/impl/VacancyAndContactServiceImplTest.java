package org.example.service.impl;

import org.example.model.VacancyAndContact;
import org.example.repository.impl.VacancyAndContactRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

class VacancyAndContactServiceImplTest {

    @Test
    void findByIdTest() {
        VacancyAndContactRepositoryImpl simpleServiceMock = Mockito.mock();
        VacancyAndContact vacancyAndContact = new VacancyAndContact();
        vacancyAndContact.setVacancyId(UUID.randomUUID());
        vacancyAndContact.setContactId(UUID.randomUUID());
        Mockito.when(simpleServiceMock.findById(vacancyAndContact.getVacancyId(), vacancyAndContact.getContactId())).thenReturn(vacancyAndContact);

        VacancyAndContact check = simpleServiceMock.findById(vacancyAndContact.getVacancyId(), vacancyAndContact.getContactId());
        VacancyAndContactServiceImpl vacancyAndContactService = new VacancyAndContactServiceImpl(simpleServiceMock);
        assertEquals(check, vacancyAndContactService.findById(vacancyAndContact.getVacancyId(), vacancyAndContact.getContactId()));
    }
    @Test
    void saveTest() {
        VacancyAndContactRepositoryImpl simpleServiceMock = Mockito.mock();
        VacancyAndContact vacancyAndContact = new VacancyAndContact();
        vacancyAndContact.setVacancyId(UUID.randomUUID());
        vacancyAndContact.setContactId(UUID.randomUUID());
        Mockito.when(simpleServiceMock.save(vacancyAndContact)).thenReturn(vacancyAndContact);

        VacancyAndContact check = simpleServiceMock.save(vacancyAndContact);
        VacancyAndContactServiceImpl vacancyAndContactService = new VacancyAndContactServiceImpl(simpleServiceMock);
        assertEquals(check, vacancyAndContactService.save(vacancyAndContact));

        ArgumentCaptor<VacancyAndContact> requestCaptor = ArgumentCaptor.forClass(VacancyAndContact.class);
        Mockito.verify(simpleServiceMock, times(2)).save(requestCaptor.capture());
        VacancyAndContact capturedArgument = requestCaptor.getValue();
        assertNotNull(capturedArgument.getVacancyId());
        assertNotNull(capturedArgument.getContactId());
    }

    @Test
    void deleteTest() {
        VacancyAndContactRepositoryImpl simpleServiceMock = Mockito.mock();
        VacancyAndContact vacancyAndContact = new VacancyAndContact();
        vacancyAndContact.setVacancyId(UUID.randomUUID());
        vacancyAndContact.setContactId(UUID.randomUUID());
        Mockito.when(simpleServiceMock.deleteById(vacancyAndContact.getVacancyId(), vacancyAndContact.getContactId())).thenReturn(true);

        boolean check = simpleServiceMock.deleteById(vacancyAndContact.getVacancyId(), vacancyAndContact.getContactId());
        VacancyAndContactServiceImpl vacancyAndContactService = new VacancyAndContactServiceImpl(simpleServiceMock);
        assertEquals(check, vacancyAndContactService.delete(vacancyAndContact.getVacancyId(), vacancyAndContact.getContactId()));
    }

    @Test
    void findAll() {
        VacancyAndContactRepositoryImpl simpleServiceMock = Mockito.mock();
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

        List<VacancyAndContact> check = simpleServiceMock.findAll();
        VacancyAndContactServiceImpl vacancyAndContactService = new VacancyAndContactServiceImpl(simpleServiceMock);
        assertEquals(check, vacancyAndContactService.findAll());
    }
}
