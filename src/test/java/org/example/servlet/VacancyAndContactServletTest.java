package org.example.servlet;

import org.example.model.VacancyAndContact;
import org.example.service.impl.VacancyAndContactServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

class VacancyAndContactServletTest {

    @Test
    void doGetTest() throws IOException {
        VacancyAndContactServiceImpl simpleServiceMock = Mockito.mock();
        VacancyAndContact vacancyAndContact = new VacancyAndContact();
        vacancyAndContact.setVacancyId(UUID.randomUUID());
        vacancyAndContact.setContactId(UUID.randomUUID());

        Mockito.when(simpleServiceMock.findById(vacancyAndContact.getVacancyId(),vacancyAndContact.getContactId())).
                thenReturn(vacancyAndContact);
        VacancyAndContactServlet vacancyAndContactServlet = new VacancyAndContactServlet(simpleServiceMock);
        vacancyAndContactServlet.doGet(new MyReq(vacancyAndContact.getVacancyId(),vacancyAndContact.getContactId()),new MyResp());
        Mockito.verify(simpleServiceMock).findById(Mockito.any(),Mockito.any());
        assertTrue(true);
    }

    @Test
    void doPostTest() throws IOException {
        VacancyAndContactServiceImpl simpleServiceMock = Mockito.mock();
        VacancyAndContact vacancyAndContact = new VacancyAndContact();
        vacancyAndContact.setVacancyId(UUID.randomUUID());
        vacancyAndContact.setContactId(UUID.randomUUID());

        Mockito.when(simpleServiceMock.save(any())).thenReturn(vacancyAndContact);
        VacancyAndContactServlet vacancyAndContactServlet = new VacancyAndContactServlet(simpleServiceMock);
        vacancyAndContactServlet.doPost(new MyReq(vacancyAndContact.getVacancyId(), vacancyAndContact.getContactId()),new MyResp());
        Mockito.verify(simpleServiceMock).save(Mockito.any());
        assertTrue(true);

    }

    @Test
    void getAllTest() throws IOException {
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

        VacancyAndContactServlet vacancyAndContactServlet = new VacancyAndContactServlet(simpleServiceMock);
        vacancyAndContactServlet.doGet(new MyReq(), new MyResp());
        Mockito.verify(simpleServiceMock).findAll();
        assertTrue(true);
    }

    @Test
    void doDeleteTest() throws IOException {
        VacancyAndContactServiceImpl simpleServiceMock = Mockito.mock();
        VacancyAndContact vacancyAndContact = new VacancyAndContact();
        vacancyAndContact.setVacancyId(UUID.randomUUID());
        vacancyAndContact.setContactId(UUID.randomUUID());
        Mockito.when(simpleServiceMock.delete(vacancyAndContact.getVacancyId(), vacancyAndContact.getContactId())).thenReturn(true);

        VacancyAndContactServlet vacancyAndContactServlet = new VacancyAndContactServlet(simpleServiceMock);
        vacancyAndContactServlet.doDelete(new MyReq(vacancyAndContact.getVacancyId(), vacancyAndContact.getContactId()),new MyResp());
        Mockito.verify(simpleServiceMock).delete(Mockito.any(),Mockito.any());
        assertTrue(true);

    }
}
