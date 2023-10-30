package org.example.repository;

import org.example.model.Contact;

import java.util.UUID;

public interface ContactRepository extends SimpleRepository<Contact, UUID> {
}
