package org.example.repository.mapper;


import org.example.db.impl.DBConnectionManagerImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SimpleResultSetMapper<T> {
    T map(ResultSet resultSet, DBConnectionManagerImpl dbConnectionManager) throws SQLException;
}
