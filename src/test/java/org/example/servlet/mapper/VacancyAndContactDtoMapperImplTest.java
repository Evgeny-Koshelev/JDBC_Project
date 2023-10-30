package org.example.servlet.mapper;

import org.example.model.VacancyAndContact;
import org.example.servlet.dto.VacancyAndContactDto;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VacancyAndContactDtoMapperImplTest {

    private final VacancyAndContactDtoMapperImpl vacancyAndContactDtoMapper = new VacancyAndContactDtoMapperImpl();

    @Test
    void toEntityTest() {
        VacancyAndContact vacancyAndContact = new VacancyAndContact();
        vacancyAndContact.setVacancyId(UUID.randomUUID());
        vacancyAndContact.setContactId(UUID.randomUUID());
        VacancyAndContactDto vacancyAndContactDto = new VacancyAndContactDto();
        vacancyAndContactDto.setVacancyId(vacancyAndContact.getVacancyId());
        vacancyAndContactDto.setContactId(vacancyAndContact.getContactId());
        VacancyAndContact check = vacancyAndContactDtoMapper.toEntity(vacancyAndContactDto);
        assertEquals(vacancyAndContact,check);
    }

    @Test
    void toDtoTest() {
        VacancyAndContact vacancyAndContact = new VacancyAndContact();
        vacancyAndContact.setVacancyId(UUID.randomUUID());
        vacancyAndContact.setContactId(UUID.randomUUID());
        VacancyAndContactDto vacancyAndContactDto = new VacancyAndContactDto();
        vacancyAndContactDto.setVacancyId(vacancyAndContact.getVacancyId());
        vacancyAndContactDto.setContactId(vacancyAndContact.getContactId());
        VacancyAndContactDto check = vacancyAndContactDtoMapper.toDto(vacancyAndContact);
        assertEquals(vacancyAndContactDto,check);
    }
}
