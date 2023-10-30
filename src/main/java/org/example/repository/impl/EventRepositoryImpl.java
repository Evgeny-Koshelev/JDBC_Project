package org.example.repository.impl;

import org.example.db.impl.DBConnectionManagerImpl;
import org.example.model.Event;
import org.example.repository.EventRepository;
import org.example.repository.mapper.EventResultSetMapperImpl;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class EventRepositoryImpl implements EventRepository {

    private final EventResultSetMapperImpl eventResultSetMapper;
    private final DBConnectionManagerImpl dbConnectionManager;
    private static final String CORE_QUERY = "SELECT e.id as e_i, e.user_id as e_u, " +
            "e.begin_date as e_b_d, e.is_completed as e_c, " +
            "e.notes as e_n, e.vacancy_id as e_v " +
            "FROM events e";

    public EventRepositoryImpl() {
        dbConnectionManager = new DBConnectionManagerImpl();
        eventResultSetMapper = new EventResultSetMapperImpl();
    }

    public EventRepositoryImpl(DBConnectionManagerImpl dbConnectionManager) {
        this.dbConnectionManager = dbConnectionManager;
        eventResultSetMapper = new EventResultSetMapperImpl();
    }
    @Override
    public Event findById(UUID id) {
        try(PreparedStatement preparedStatement = dbConnectionManager.getConnection().prepareStatement(
                CORE_QUERY + " WHERE e.id = ?")) {
            preparedStatement.setObject(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return eventResultSetMapper.map(resultSet, dbConnectionManager);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(UUID id) {

        if(findById(id) != null) {
            try(PreparedStatement preparedStatement = dbConnectionManager.getConnection().prepareStatement(
                    "DELETE FROM events WHERE id = ?")) {
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
    public List<Event> findAll() {
        try(PreparedStatement preparedStatement = dbConnectionManager.getConnection().prepareStatement(
                CORE_QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return eventResultSetMapper.getAll(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Event save(Event event) {
        try(PreparedStatement preparedStatement = dbConnectionManager.getConnection().prepareStatement(
                    "insert into events( id, user_id, begin_date ,is_completed, notes, " +
                            "vacancy_id) values(?,?,?,?,?,?)")) {
            preparedStatement.setObject(1,event.getId());
            preparedStatement.setString(2,event.getUserId());
            preparedStatement.setTimestamp(3, Timestamp.from(event.getBeginDate().toInstant()));
            preparedStatement.setBoolean(4,event.getIsCompleted());
            preparedStatement.setString(5,event.getNotes());
            preparedStatement.setObject(6,event.getVacancyId());
            preparedStatement.execute();
            return (findById(event.getId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Event> findByVacancyId(UUID id) {
        try(PreparedStatement preparedStatement = dbConnectionManager.getConnection().prepareStatement(
                CORE_QUERY + " WHERE e.vacancy_id = ?")) {
            preparedStatement.setObject(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return eventResultSetMapper.getAll(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
