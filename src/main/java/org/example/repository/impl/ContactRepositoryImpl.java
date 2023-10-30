package org.example.repository.impl;

import org.example.db.impl.DBConnectionManagerImpl;
import org.example.model.Contact;
import org.example.repository.ContactRepository;
import org.example.repository.mapper.ContactResultSetMapperImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;


public class ContactRepositoryImpl implements ContactRepository {

    private final DBConnectionManagerImpl dbConnectionManager;
    private final ContactResultSetMapperImpl contactResultSetMapper;

    private static final String CORE_QUERY = "SELECT c.id as c_i, c.user_id as c_u, " +
            "c.name as c_n, c.mail as c_m, c.notes as c_nt, c.telephone as c_t, " +
            "c.company as c_c " +
            "FROM contacts c";

    public ContactRepositoryImpl() {
        dbConnectionManager = new DBConnectionManagerImpl();
        contactResultSetMapper = new ContactResultSetMapperImpl();
    }

    public ContactRepositoryImpl(DBConnectionManagerImpl dbConnectionManager) {
        this.dbConnectionManager = dbConnectionManager;
        contactResultSetMapper = new ContactResultSetMapperImpl();
    }
    @Override
    public Contact findById(UUID id) {
        try(PreparedStatement preparedStatement = dbConnectionManager.getConnection().prepareStatement(
                CORE_QUERY + " WHERE c.id = ?")) {
            preparedStatement.setObject(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return contactResultSetMapper.map(resultSet, dbConnectionManager);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean deleteById(UUID id) {

        if(findById(id) != null) {
            try(PreparedStatement preparedStatement = dbConnectionManager.getConnection().prepareStatement(
                    "DELETE FROM contacts WHERE id = ?")) {
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
    public List<Contact> findAll() {
        try(PreparedStatement preparedStatement = dbConnectionManager.getConnection().prepareStatement(
                CORE_QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return contactResultSetMapper.getAll(resultSet, true, dbConnectionManager);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Contact save(Contact contact) {
        try(PreparedStatement preparedStatement = dbConnectionManager.getConnection().prepareStatement(
                "insert into contacts( id, user_id, name , company, mail, telephone, notes) values(?,?,?,?,?,?,?)")) {
            preparedStatement.setObject(1,contact.getId());
            preparedStatement.setString(2,contact.getUserId());
            preparedStatement.setString(3,contact.getName());
            preparedStatement.setString(4,contact.getCompany());
            preparedStatement.setString(5,contact.getMail());
            preparedStatement.setString(6,contact.getTelephone());
            preparedStatement.setString(7,contact.getNotes());
            preparedStatement.execute();
            return (findById(contact.getId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Contact> findByVacancyId(UUID id) {
        try(PreparedStatement preparedStatement = dbConnectionManager.getConnection().prepareStatement(
                CORE_QUERY +
                        " LEFT JOIN vacancies_contacts vc ON c.id = vc.contact_id" +
                        " LEFT JOIN vacancies v on vc.vacancy_id= v.id" +
                        " WHERE v.id = ?")) {
            preparedStatement.setObject(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return contactResultSetMapper.getAll(resultSet, false, dbConnectionManager);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
