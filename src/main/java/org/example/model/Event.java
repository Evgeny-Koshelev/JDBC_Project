package org.example.model;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public class Event {
    private UUID id;

    private String userId;

    private UUID vacancyId;

    private ZonedDateTime beginDate;

    private String notes;

    private Boolean isCompleted;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getVacancyId() {
        return vacancyId;
    }

    public void setVacancyId(UUID vacancyId) {
        this.vacancyId = vacancyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ZonedDateTime getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(ZonedDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass().equals(this.getClass())) {
            Event event = (Event) obj;
            return (this.getId().equals(event.getId()));
        }
        else
            return false;
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }
}
