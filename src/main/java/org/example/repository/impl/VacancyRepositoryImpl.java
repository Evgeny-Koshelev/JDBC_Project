package org.example.repository.impl;

import org.example.db.impl.DBConnectionManagerImpl;
import org.example.model.Vacancy;
import org.example.repository.VacancyRepository;
import org.example.repository.mapper.VacancyResultSetMapperImpl;

import java.sql.*;
import java.util.List;
import java.util.UUID;

public class VacancyRepositoryImpl implements VacancyRepository {
    private final VacancyResultSetMapperImpl vacancyResultSetMapper;
    private final DBConnectionManagerImpl dbConnectionManager;
    private static final String CORE_QUERY = "SELECT v.id as v_i, v.user_id as v_u, " +
            "v.name_vacancy as v_n_v, v.status_id as v_s_i, " +
            "v.notes as v_n, v.salary as v_s, v.company as v_c " +
            "FROM vacancies v";

    public VacancyRepositoryImpl() {
        dbConnectionManager = new DBConnectionManagerImpl();
        vacancyResultSetMapper = new VacancyResultSetMapperImpl();
    }

    public VacancyRepositoryImpl(DBConnectionManagerImpl dbConnectionManager) {
        this.dbConnectionManager = dbConnectionManager;
        vacancyResultSetMapper = new VacancyResultSetMapperImpl();
    }
    @Override
    public Vacancy findById(UUID id) {
            try(PreparedStatement preparedStatement = dbConnectionManager.getConnection().prepareStatement(
                    CORE_QUERY + " WHERE v.id = ?")) {
                preparedStatement.setObject(1,id);
                ResultSet resultSet = preparedStatement.executeQuery();
               return vacancyResultSetMapper.map(resultSet, dbConnectionManager);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(UUID id) {
        if(findById(id) != null) {
            try(PreparedStatement preparedStatement = dbConnectionManager.getConnection().prepareStatement(
                    "DELETE FROM vacancies WHERE id = ?")) {
                preparedStatement.setObject(1,id);
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
    public List<Vacancy> findAll() {
        try(PreparedStatement preparedStatement = dbConnectionManager.getConnection().prepareStatement(
                CORE_QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return vacancyResultSetMapper.getAll(resultSet, true, dbConnectionManager);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        try(PreparedStatement preparedStatement = dbConnectionManager.getConnection().prepareStatement(
                "insert into vacancies( id, user_id, name_vacancy, status_id, " +
                        "company, salary, notes) values(?,?,?,?,?,?,?)")) {
            preparedStatement.setObject(1,vacancy.getId());
            preparedStatement.setString(2,vacancy.getUserId());
            preparedStatement.setString(3,vacancy.getNameVacancy());
            preparedStatement.setObject(4,vacancy.getStatusId());
            preparedStatement.setString(5,vacancy.getCompany());
            preparedStatement.setInt(6,vacancy.getSalary());
            preparedStatement.setString(7,vacancy.getNotes());
            preparedStatement.execute();
            return (findById(vacancy.getId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Vacancy> findByContactId(UUID id) {
        try(PreparedStatement preparedStatement = dbConnectionManager.getConnection().prepareStatement(
                CORE_QUERY +
                        " LEFT JOIN vacancies_contacts vc ON v.id = vc.vacancy_id" +
                        " LEFT JOIN contacts c on vc.contact_id= c.id" +
                        " WHERE c.id = ?")) {
            preparedStatement.setObject(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return vacancyResultSetMapper.getAll(resultSet, false, dbConnectionManager);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Vacancy> findByStatusId(UUID id) {
        try(PreparedStatement preparedStatement = dbConnectionManager.getConnection().prepareStatement(
                CORE_QUERY + " WHERE v.status_id = ?")) {
            preparedStatement.setObject(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return vacancyResultSetMapper.getAll(resultSet, true, dbConnectionManager);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
