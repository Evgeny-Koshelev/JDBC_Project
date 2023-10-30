package org.example.repository.mapper;

import org.example.db.impl.DBConnectionManagerImpl;
import org.example.model.Status;
import org.example.model.Vacancy;
import org.example.repository.impl.VacancyRepositoryImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class StatusResultSetMapperImpl implements SimpleResultSetMapper<Status>{

    public Status map(ResultSet resultSet, DBConnectionManagerImpl dbConnectionManager) throws SQLException {
        List<Status> statusList = getAll(resultSet, dbConnectionManager);
        if(statusList.isEmpty())
            return null;
        else
            return statusList.get(0);
    }

    public List<Status> getAll(ResultSet resultSet,  DBConnectionManagerImpl dbConnectionManager) throws SQLException {
        List<Status> statusList = new ArrayList<>();
        VacancyRepositoryImpl vacancyRepository = new VacancyRepositoryImpl(dbConnectionManager);
        while(resultSet.next()) {
            Status status = new Status();
            UUID id = UUID.fromString(resultSet.getObject("s_i").toString());
            status.setId(id);
            status.setUserId(resultSet.getString("s_u"));
            status.setNameStatus(resultSet.getString("s_n"));
            status.setOrderNum(resultSet.getInt("s_o_n"));
            List<Vacancy> vacancyList = vacancyRepository.findByStatusId(id);
            Set<Vacancy> vacancySet = new HashSet<>(vacancyList);
            status.setVacancies(vacancySet);
            statusList.add(status);
        }

        if(!statusList.isEmpty())
            return statusList;
        else
            return Collections.emptyList();
    }
}
