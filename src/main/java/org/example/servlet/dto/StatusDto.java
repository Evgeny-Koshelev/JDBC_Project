package org.example.servlet.dto;

import org.example.model.Vacancy;

import java.util.Set;
import java.util.UUID;

public class StatusDto {

    private UUID id;

    private String userId;

    private String nameStatus;

    private Integer orderNum;

    Set<Vacancy> vacancies;
    public Set<Vacancy> getVacancies() { return vacancies; }

    public void setVacancies(Set<Vacancy> vacancies) { this.vacancies = vacancies; }

    public String getNameStatus() {
        return nameStatus;
    }

    public void setNameStatus(String nameStatus) {
        this.nameStatus = nameStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public UUID getId() { return id; }

    public void setId(UUID id) { this.id = id; }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass().equals(this.getClass())) {
            StatusDto status = (StatusDto) obj;
            return (this.getId().equals(status.getId()));
        }
        else
            return false;
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }

}
