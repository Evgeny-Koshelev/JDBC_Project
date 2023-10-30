package org.example.model;

import java.util.Set;
import java.util.UUID;

public class Contact {
    private UUID id;

    private String userId;

    private String name;

    private String company;

    private String mail;

    private String telephone;

    private String notes;


    private Set<Vacancy> vacancies;


    public UUID getId() {
        return id;
    }



    public void setId(UUID id) {
        this.id = id;
    }


    public String getUserId() {
        return userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }


    public String getNotes() {
        return notes;
    }


    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Set<Vacancy> getVacancies() { return vacancies; }

    public void setVacancies(Set<Vacancy> vacancies) { this.vacancies = vacancies; }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass().equals(this.getClass())) {
            Contact contact = (Contact) obj;
            return (this.getId().equals(contact.getId()));
        }
        else
            return false;
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }


}
