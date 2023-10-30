package org.example.repository.mapper;

import org.example.db.impl.DBConnectionManagerImpl;
import org.example.model.VacancyAndContact;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class VacancyAndContactResultSetMapperImpl implements SimpleResultSetMapper<VacancyAndContact>{
    @Override
    public VacancyAndContact map(ResultSet resultSet, DBConnectionManagerImpl dbConnectionManager) throws SQLException {
        List<VacancyAndContact> vacancyAndContactList = getAll(resultSet);
        if(vacancyAndContactList.isEmpty())
            return null;
        else
            return vacancyAndContactList.get(0);
    }

    public List<VacancyAndContact> getAll(ResultSet resultSet) throws SQLException {
        List<VacancyAndContact> vacancyAndContactList = new ArrayList<>();
        while(resultSet.next()) {
            VacancyAndContact vacancyAndContact = new VacancyAndContact();
            UUID vacancyId = UUID.fromString(resultSet.getObject("vacancy_id").toString());
            UUID contactId = UUID.fromString(resultSet.getObject("contact_id").toString());
            vacancyAndContact.setVacancyId(vacancyId);
            vacancyAndContact.setContactId(contactId);
            vacancyAndContactList.add(vacancyAndContact);
        }
        if(!vacancyAndContactList.isEmpty())
            return vacancyAndContactList;
        else
            return Collections.emptyList();
    }
}
