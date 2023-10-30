package org.example.repository.mapper;

import org.example.db.impl.DBConnectionManagerImpl;
import org.example.model.Contact;
import org.example.model.Vacancy;
import org.example.repository.impl.VacancyRepositoryImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ContactResultSetMapperImpl implements SimpleResultSetMapper<Contact> {


    public Contact map(ResultSet resultSet, DBConnectionManagerImpl dbConnectionManager) throws SQLException {
        List<Contact> contactList = getAll(resultSet, true, dbConnectionManager);
        if(contactList.isEmpty())
            return null;
        else
            return contactList.get(0);
    }

    public List<Contact> getAll(ResultSet resultSet, boolean forThis, DBConnectionManagerImpl dbConnectionManager) throws SQLException {
        List<Contact> contactList = new ArrayList<>();
        VacancyRepositoryImpl vacancyRepository = new VacancyRepositoryImpl(dbConnectionManager);
        while(resultSet.next()) {
            Contact contact = new Contact();
            UUID id = UUID.fromString(resultSet.getObject("c_i").toString());
            contact.setId(id);
            contact.setUserId(resultSet.getString("c_u"));
            contact.setName(resultSet.getString("c_n"));
            contact.setCompany(resultSet.getString("c_c"));
            contact.setMail(resultSet.getString("c_m"));
            contact.setTelephone(resultSet.getString("c_t"));
            contact.setNotes(resultSet.getString("c_nt"));
            if(forThis) {
                List<Vacancy> vacancyList = vacancyRepository.findByContactId(id);
                Set<Vacancy> vacancySet = new HashSet<>(vacancyList);
                contact.setVacancies(vacancySet);
            }
            contactList.add(contact);
        }

        if(!contactList.isEmpty())
            return contactList;
        else
            return Collections.emptyList();
    }
}
