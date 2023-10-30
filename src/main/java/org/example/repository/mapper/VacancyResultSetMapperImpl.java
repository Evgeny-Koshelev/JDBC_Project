package org.example.repository.mapper;

import org.example.db.impl.DBConnectionManagerImpl;
import org.example.model.Contact;
import org.example.model.Event;
import org.example.model.Vacancy;
import org.example.repository.impl.ContactRepositoryImpl;
import org.example.repository.impl.EventRepositoryImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class VacancyResultSetMapperImpl implements SimpleResultSetMapper<Vacancy> {

    public Vacancy map(ResultSet resultSet, DBConnectionManagerImpl dbConnectionManager) throws SQLException {
        List<Vacancy> vacancyList = getAll(resultSet, true, dbConnectionManager);
        if(vacancyList.isEmpty())
            return null;
        else
            return vacancyList.get(0);
    }

    public List<Vacancy> getAll(ResultSet resultSet, boolean forThis, DBConnectionManagerImpl dbConnectionManager) throws SQLException {
        EventRepositoryImpl eventRepository = new EventRepositoryImpl(dbConnectionManager);
        ContactRepositoryImpl contactRepository = new ContactRepositoryImpl(dbConnectionManager);
        List<Vacancy> vacancyList = new ArrayList<>();
        while(resultSet.next()) {
            Vacancy vacancy = new Vacancy();
            UUID id = UUID.fromString(resultSet.getObject("v_i").toString());
            UUID statusId = UUID.fromString(resultSet.getObject("v_s_i").toString());
            vacancy.setId(id);
            vacancy.setStatusId(statusId);
            vacancy.setUserId(resultSet.getString("v_u"));
            vacancy.setNameVacancy(resultSet.getString("v_n_v"));
            vacancy.setCompany(resultSet.getString("v_c"));
            vacancy.setSalary(resultSet.getInt("v_s"));
            vacancy.setNotes(resultSet.getString("v_n"));
            List<Event> eventList = eventRepository.findByVacancyId(id);
            Set<Event> eventSet = new HashSet<>(eventList);
            vacancy.setEvents(eventSet);
            if(forThis) {
                List<Contact> contactList = contactRepository.findByVacancyId(id);
                Set<Contact> contactSet = new HashSet<>(contactList);
                vacancy.setContacts(contactSet);
            }
            vacancyList.add(vacancy);
        }
        if(!vacancyList.isEmpty())
            return vacancyList;
        else
            return Collections.emptyList();

    }
}
