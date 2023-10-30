package org.example.repository.impl;

import org.example.db.impl.DBConnectionManagerImpl;
import org.example.model.VacancyAndContact;
import org.example.repository.VacancyAndContactRepository;
import org.example.repository.mapper.VacancyAndContactResultSetMapperImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class VacancyAndContactRepositoryImpl implements VacancyAndContactRepository {

    private final VacancyAndContactResultSetMapperImpl vacancyAndContactResultSetMapper;
    private final DBConnectionManagerImpl dbConnectionManager;
    private static final String CORE_QUERY = "SELECT vc.vacancy_id, vc.contact_id " +
            "FROM vacancies_contacts vc";

    public VacancyAndContactRepositoryImpl() {
        dbConnectionManager = new DBConnectionManagerImpl();
        vacancyAndContactResultSetMapper = new VacancyAndContactResultSetMapperImpl();
    }

    public VacancyAndContactRepositoryImpl(DBConnectionManagerImpl dbConnectionManager) {
        this.dbConnectionManager = dbConnectionManager;
        vacancyAndContactResultSetMapper = new VacancyAndContactResultSetMapperImpl();
    }

    @Override
    public VacancyAndContact findById(UUID id) {
        return null;
    }

    @Override
    public boolean deleteById(UUID id) {
        return false;
    }

    public VacancyAndContact findById(UUID vacancyId, UUID contactId) {
        try(PreparedStatement preparedStatement = dbConnectionManager.getConnection().prepareStatement(
                CORE_QUERY + " WHERE vc.vacancy_id = ? and vc.contact_id =?")) {
            preparedStatement.setObject(1,vacancyId);
            preparedStatement.setObject(2,contactId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return vacancyAndContactResultSetMapper.map(resultSet, dbConnectionManager);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean deleteById(UUID vacancyId, UUID contactId) {
        if(findById(vacancyId,contactId) != null) {
            try(PreparedStatement preparedStatement = dbConnectionManager.getConnection().prepareStatement(
                    "DELETE FROM vacancies_contacts WHERE vacancy_id = ? and contact_id = ?")) {
                preparedStatement.setObject(1,vacancyId);
                preparedStatement.setObject(2,contactId);
                preparedStatement.execute();
                return true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        else
            return false;
    }

    @Override
    public List<VacancyAndContact> findAll() {
        try(PreparedStatement preparedStatement = dbConnectionManager.getConnection().prepareStatement(
                CORE_QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return vacancyAndContactResultSetMapper.getAll(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public VacancyAndContact save(VacancyAndContact vacancyAndContact) {
        try(PreparedStatement preparedStatement = dbConnectionManager.getConnection().prepareStatement(
                "insert into vacancies_contacts( vacancy_id, contact_id) values(?,?)")) {
            preparedStatement.setObject(1,vacancyAndContact.getVacancyId());
            preparedStatement.setObject(2,vacancyAndContact.getContactId());
            preparedStatement.execute();
            return (findById(vacancyAndContact.getVacancyId(),vacancyAndContact.getContactId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
