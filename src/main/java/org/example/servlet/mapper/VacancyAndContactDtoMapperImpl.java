package org.example.servlet.mapper;

import org.example.model.VacancyAndContact;
import org.example.servlet.dto.VacancyAndContactDto;

public class VacancyAndContactDtoMapperImpl implements SimpleDtoMapper<VacancyAndContact, VacancyAndContactDto> {
    @Override
    public VacancyAndContact toEntity(VacancyAndContactDto incomingDto) {
        VacancyAndContact vacancyAndContact = new VacancyAndContact();
        vacancyAndContact.setVacancyId(incomingDto.getVacancyId());
        vacancyAndContact.setContactId(incomingDto.getContactId());
        return vacancyAndContact;
    }

    @Override
    public VacancyAndContactDto toDto(VacancyAndContact simpleEntity) {
        VacancyAndContactDto vacancyAndContactDto = new VacancyAndContactDto();
        vacancyAndContactDto.setVacancyId(simpleEntity.getVacancyId());
        vacancyAndContactDto.setContactId(simpleEntity.getContactId());
        return vacancyAndContactDto;
    }
}
