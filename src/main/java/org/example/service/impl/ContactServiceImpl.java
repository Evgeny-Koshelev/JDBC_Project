package org.example.service.impl;

import org.example.model.Contact;
import org.example.repository.ContactRepository;
import org.example.repository.impl.ContactRepositoryImpl;
import org.example.service.SimpleService;

import java.util.List;
import java.util.UUID;

public class ContactServiceImpl implements SimpleService<Contact> {

    private final ContactRepository contactRepository;

    public ContactServiceImpl(ContactRepositoryImpl contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Contact save(Contact contact) { return contactRepository.save(contact); }

    @Override
    public Contact findById(UUID uuid) { return contactRepository.findById(uuid); }

    @Override
    public List<Contact> findAll() { return contactRepository.findAll(); }

    @Override
    public Boolean delete(UUID id) { return contactRepository.deleteById(id); }
}
