package org.example.servlet.mapper;

import org.example.model.Vacancy;
import org.example.servlet.dto.VacancyDto;

public class VacancyDtoMapperImpl implements SimpleDtoMapper<Vacancy,VacancyDto> {
    @Override
    public Vacancy toEntity(VacancyDto incomingDto) {
        Vacancy vacancy = new Vacancy();
        vacancy.setId(incomingDto.getId());
        vacancy.setUserId(incomingDto.getUserId());
        vacancy.setNameVacancy(incomingDto.getNameVacancy());
        vacancy.setStatusId(incomingDto.getStatusId());
        vacancy.setCompany(incomingDto.getCompany());
        vacancy.setSalary(incomingDto.getSalary());
        vacancy.setNotes(incomingDto.getNotes());
        return vacancy;
    }

    @Override
    public VacancyDto toDto(Vacancy simpleEntity) {
        VacancyDto vacancyDto = new VacancyDto();
        vacancyDto.setId(simpleEntity.getId());
        vacancyDto.setUserId(simpleEntity.getUserId());
        vacancyDto.setNameVacancy(simpleEntity.getNameVacancy());
        vacancyDto.setStatusId(simpleEntity.getStatusId());
        vacancyDto.setCompany(simpleEntity.getCompany());
        vacancyDto.setSalary(simpleEntity.getSalary());
        vacancyDto.setNotes(simpleEntity.getNotes());
        vacancyDto.setEvents(simpleEntity.getEvents());
        vacancyDto.setContacts(simpleEntity.getContacts());
        return vacancyDto;

    }

}
