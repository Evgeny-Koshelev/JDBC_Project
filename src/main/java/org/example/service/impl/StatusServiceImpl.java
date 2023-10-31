package org.example.service.impl;

import org.example.model.Status;
import org.example.repository.StatusRepository;
import org.example.repository.impl.StatusRepositoryImpl;
import org.example.service.SimpleService;

import java.util.List;
import java.util.UUID;

public class StatusServiceImpl implements SimpleService<Status> {
    private final StatusRepository statusRepository ;

    public StatusServiceImpl(StatusRepositoryImpl statusRepository) {
        this.statusRepository = statusRepository;
    }
    @Override
    public Status save(Status status) {
        return statusRepository.save(status);
    }

    @Override
    public Status findById(UUID uuid) { return statusRepository.findById(uuid); }

    @Override
    public List<Status> findAll(){return statusRepository.findAll(); }

    @Override
    public Boolean delete(UUID id) {return statusRepository.deleteById(id); }
}
