package org.example.repository.mapper;

import org.example.db.impl.DBConnectionManagerImpl;
import org.example.model.Event;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class EventResultSetMapperImpl implements SimpleResultSetMapper<Event>{
    @Override
    public Event map(ResultSet resultSet, DBConnectionManagerImpl dbConnectionManager) throws SQLException {
        List<Event> eventList = getAll(resultSet);
        if(eventList.isEmpty())
            return null;
        else
            return eventList.get(0);

    }

    public List<Event> getAll(ResultSet resultSet) throws SQLException {
        List<Event> eventList = new ArrayList<>();
        while(resultSet.next()) {
            Event event = new Event();
            UUID id = UUID.fromString(resultSet.getObject("e_i").toString());
            event.setId(id);
            event.setUserId(resultSet.getString("e_u"));
            ZonedDateTime eventDate = ZonedDateTime.ofInstant(resultSet.getTimestamp("e_b_d").toInstant(), ZoneId.of("UTC+3"));
            event.setBeginDate(eventDate);
            event.setIsCompleted(resultSet.getBoolean("e_c"));
            event.setNotes(resultSet.getString("e_n"));
            UUID vId = UUID.fromString(resultSet.getObject("e_v").toString());
            event.setVacancyId(vId);
            eventList.add(event);
        }
        if(!eventList.isEmpty())
            return eventList;
        else
            return Collections.emptyList();
    }
}
