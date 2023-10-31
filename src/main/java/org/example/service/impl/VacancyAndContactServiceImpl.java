package org.example.service.impl;

import org.example.model.VacancyAndContact;
import org.example.repository.VacancyAndContactRepository;
import org.example.repository.impl.VacancyAndContactRepositoryImpl;
import org.example.service.SimpleService;

import java.util.List;
import java.util.UUID;

public class VacancyAndContactServiceImpl implements SimpleService<VacancyAndContact> {
    private final VacancyAndContactRepositoryImpl vacancyAndContactRepository;

    public VacancyAndContactServiceImpl(VacancyAndContactRepositoryImpl vacancyAndContactRepository) {
        this.vacancyAndContactRepository = vacancyAndContactRepository;
    }
    @Override
    public VacancyAndContact save(VacancyAndContact vacancyAndContact) { return vacancyAndContactRepository.save(vacancyAndContact); }

    @Override
    public VacancyAndContact findById(UUID uuid) {
        return null;
    }

    public VacancyAndContact findById(UUID vacancyId, UUID contactId) { return vacancyAndContactRepository.findById(vacancyId, contactId); }

    @Override
    public List<VacancyAndContact> findAll() {
        return vacancyAndContactRepository.findAll();
    }

    @Override
    public Boolean delete(UUID uuid) { return false; }

    public Boolean delete(UUID vacancyId, UUID contactId) { return vacancyAndContactRepository.deleteById(vacancyId,contactId); }
}
