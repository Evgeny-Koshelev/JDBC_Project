package org.example.db;

import org.example.db.impl.DBConnectionManagerImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class CreateTables {
    private final DBConnectionManagerImpl dbConnectionManager;
    public CreateTables(DBConnectionManagerImpl dbConnectionManager) {
        this.dbConnectionManager = dbConnectionManager;
    }

    public void create() {
        try (PreparedStatement pstmt  = this.dbConnectionManager.getConnection().prepareStatement(
                "CREATE TABLE if not exists statuses\n" +
                        "(\n" +
                        "    id uuid not null,\n" +
                        "    name_status text not null,\n" +
                        "    user_id text not null,\n" +
                        "    order_num integer not null,\n" +
                        "    CONSTRAINT pk_status primary key(id)\n" +
                        "\n" +
                        ");" +
                        "CREATE TABLE if not exists vacancies\n" +
                        "(\n" +
                        "    id uuid not null,\n" +
                        "    user_id text not null,\n" +
                        "    name_vacancy text not null,\n" +
                        "    status_id uuid,\n" +
                        "    company text,\n" +
                        "    salary integer,\n" +
                        "    notes text not null,\n" +
                        "    primary key(id),\n" +
                        "    CONSTRAINT fk_status_name FOREIGN KEY(status_id)  REFERENCES statuses(id)\n" +
                        ");\n" +
                        "\n" +
                        "\n" +
                        "CREATE TABLE if not exists events\n" +
                        "(\n" +
                        "    id uuid not null,\n" +
                        "    user_id text not null,\n" +
                        "    begin_date timestamptz,\n" +
                        "    vacancy_id uuid,\n" +
                        "    is_completed boolean default false,\n" +
                        "    notes  text not null,\n" +
                        "    primary key(id),\n" +
                        "    CONSTRAINT fk_vacancy FOREIGN KEY(vacancy_id)  REFERENCES vacancies(id)\n" +
                        ");\n" +
                        "\n" +
                        "CREATE TABLE if not exists contacts\n" +
                        "(\n" +
                        "    id uuid not null,\n" +
                        "    user_id text not null,\n" +
                        "    name text not null,\n" +
                        "    company text,\n" +
                        "    mail text,\n" +
                        "    telephone text,\n" +
                        "    notes text,\n" +
                        "    primary key(id)\n" +
                        ");\n" +
                        "\n" +
                        "CREATE TABLE if not exists vacancies_contacts\n" +
                        "(\n" +
                        "    vacancy_id uuid not null,\n" +
                        "    contact_id uuid not null,\n" +
                        "    CONSTRAINT pk_v_c primary key(vacancy_id,contact_id),\n" +
                        "    CONSTRAINT fk_vacancy FOREIGN KEY(vacancy_id)  REFERENCES vacancies(id),\n" +
                        "    CONSTRAINT fk_contact FOREIGN KEY(contact_id)  REFERENCES contacts(id)\n" +
                        ");"
        )) {
            pstmt.execute();
            fillTables();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillTables() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src\\test\\resources\\dataForDB.txt"));
            String line = reader.readLine();
            StringBuilder lines = new StringBuilder();
            while (line != null) {
                lines.append(line);
                line = reader.readLine();
            }
            try (PreparedStatement pstmt  = this.dbConnectionManager.getConnection().prepareStatement(
                    String.valueOf(lines)
            )) {
                pstmt.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void delete() {
            try (PreparedStatement pstmt  = this.dbConnectionManager.getConnection().prepareStatement(
                    "DELETE FROM events;" +
                            "DELETE FROM vacancies_contacts;" +
                            "DELETE FROM contacts;" +
                            "DELETE FROM vacancies;" +
                            "DELETE FROM statuses;"
            )) {
                pstmt.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

    }
}
