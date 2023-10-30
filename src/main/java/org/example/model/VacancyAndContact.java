package org.example.model;

import java.util.UUID;

public class VacancyAndContact {

    private UUID vacancyId;
    private UUID contactId;
    public UUID getVacancyId() { return vacancyId; }

    public void setVacancyId(UUID vacancyId) { this.vacancyId = vacancyId; }

    public UUID getContactId() { return contactId; }

    public void setContactId(UUID contactId) { this.contactId = contactId; }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass().equals(this.getClass())) {
            VacancyAndContact vacancyAndContact = (VacancyAndContact) obj;
            return (this.getVacancyId().equals(vacancyAndContact.getVacancyId()) &&
                    this.getContactId().equals(vacancyAndContact.getContactId()));
        }
        else
            return false;
    }

    @Override
    public int hashCode() {
        return this.getVacancyId().hashCode() + this.getContactId().hashCode();
    }

}
