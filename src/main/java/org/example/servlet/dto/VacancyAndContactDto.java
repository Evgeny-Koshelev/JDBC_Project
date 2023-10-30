package org.example.servlet.dto;


import java.util.UUID;

public class VacancyAndContactDto {

    private UUID vacancyId;
    private UUID contactId;

    public UUID getVacancyId() { return vacancyId; }

    public void setVacancyId(UUID vacancyId) { this.vacancyId = vacancyId; }

    public UUID getContactId() { return contactId; }

    public void setContactId(UUID contactId) { this.contactId = contactId; }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass().equals(this.getClass())) {
            VacancyAndContactDto vacancyAndContact = (VacancyAndContactDto) obj;
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
