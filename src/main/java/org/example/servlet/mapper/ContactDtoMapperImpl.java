package org.example.servlet.mapper;

import org.example.model.Contact;
import org.example.servlet.dto.ContactDto;

public class ContactDtoMapperImpl implements SimpleDtoMapper<Contact, ContactDto> {
    @Override
    public Contact toEntity(ContactDto incomingDto) {
        Contact contact = new Contact();
        contact.setId(incomingDto.getId());
        contact.setUserId(incomingDto.getUserId());
        contact.setName(incomingDto.getName());
        contact.setCompany(incomingDto.getCompany());
        contact.setMail(incomingDto.getMail());
        contact.setTelephone(incomingDto.getTelephone());
        contact.setNotes(incomingDto.getNotes());
        return contact;
    }

    @Override
    public ContactDto toDto(Contact simpleEntity) {
        ContactDto contactDto = new ContactDto();
        contactDto.setId(simpleEntity.getId());
        contactDto.setUserId(simpleEntity.getUserId());
        contactDto.setVacancies(simpleEntity.getVacancies());
        contactDto.setName(simpleEntity.getName());
        contactDto.setCompany(simpleEntity.getCompany());
        contactDto.setMail(simpleEntity.getMail());
        contactDto.setTelephone(simpleEntity.getTelephone());
        contactDto.setNotes(simpleEntity.getNotes());
        return contactDto;
    }
}
