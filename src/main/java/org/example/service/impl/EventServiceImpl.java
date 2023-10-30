package org.example.service.impl;

import org.example.model.Event;
import org.example.repository.EventRepository;
import org.example.repository.impl.EventRepositoryImpl;
import org.example.service.SimpleService;

import java.util.List;
import java.util.UUID;

public class EventServiceImpl implements SimpleService<Event> {

    private final EventRepository eventRepository = new EventRepositoryImpl();
    @Override
    public Event save(Event event) { return eventRepository.save(event); }

    @Override
    public Event findById(UUID uuid) {
        return eventRepository.findById(uuid);
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public Boolean delete(UUID id) {
        return eventRepository.deleteById(id);
    }
}
