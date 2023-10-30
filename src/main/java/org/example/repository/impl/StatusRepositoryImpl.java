package org.example.repository.impl;

import org.example.db.impl.DBConnectionManagerImpl;
import org.example.model.Status;
import org.example.repository.StatusRepository;
import org.example.repository.mapper.StatusResultSetMapperImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class StatusRepositoryImpl implements StatusRepository {

    private final DBConnectionManagerImpl dbConnectionManager;
    private final StatusResultSetMapperImpl statusResultSetMapper;
    private static final String CORE_QUERY = "SELECT s.id as s_i, s.user_id as s_u, " +
            "s.name_status as s_n, s.order_num as s_o_n " +
            "FROM statuses s";

    public StatusRepositoryImpl() {
        dbConnectionManager = new DBConnectionManagerImpl();
        statusResultSetMapper = new StatusResultSetMapperImpl();
    }

    public StatusRepositoryImpl(DBConnectionManagerImpl dbConnectionManager) {
        this.dbConnectionManager = dbConnectionManager;
        statusResultSetMapper = new StatusResultSetMapperImpl();
    }

    @Override
    public Status findById(UUID id) {
        try(PreparedStatement preparedStatement = dbConnectionManager.getConnection().prepareStatement(
                CORE_QUERY + " WHERE s.id = ?")) {
            preparedStatement.setObject(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return statusResultSetMapper.map(resultSet, dbConnectionManager);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(UUID id) {
        if(findById(id) != null) {
            try(PreparedStatement preparedStatement = dbConnectionManager.getConnection().prepareStatement(
                    "DELETE FROM statuses WHERE id = ?")) {
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
    public List<Status> findAll() {
        try(PreparedStatement preparedStatement = dbConnectionManager.getConnection().prepareStatement(
                CORE_QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return statusResultSetMapper.getAll(resultSet, dbConnectionManager);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Status save(Status status) {
        try(PreparedStatement preparedStatement = dbConnectionManager.getConnection().prepareStatement(
                "insert into statuses( id, user_id, name_status , order_num ) values(?,?,?,?)")) {
            preparedStatement.setObject(1,status.getId());
            preparedStatement.setString(2,status.getUserId());
            preparedStatement.setString(3,status.getNameStatus());
            preparedStatement.setInt(4,status.getOrderNum());
            preparedStatement.execute();
            return (findById(status.getId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
