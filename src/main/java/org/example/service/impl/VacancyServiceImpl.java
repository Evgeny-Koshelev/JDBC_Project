package org.example.service.impl;

import org.example.model.Vacancy;
import org.example.repository.VacancyRepository;
import org.example.repository.impl.VacancyRepositoryImpl;
import org.example.service.SimpleService;

import java.util.List;
import java.util.UUID;

public class VacancyServiceImpl implements SimpleService<Vacancy> {

    private final VacancyRepository vacancyRepository;

    public VacancyServiceImpl(VacancyRepositoryImpl vacancyRepository) {
        this.vacancyRepository = vacancyRepository;

    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        return vacancyRepository.save(vacancy);
    }

    @Override
    public Vacancy findById(UUID uuid) { return vacancyRepository.findById(uuid); }

    @Override
    public List<Vacancy> findAll() {
        return vacancyRepository.findAll();
    }

    @Override
    public Boolean delete(UUID id) {
        return vacancyRepository.deleteById(id);
    }
}
