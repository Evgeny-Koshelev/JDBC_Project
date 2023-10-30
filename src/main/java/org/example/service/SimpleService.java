package org.example.service;


import java.util.List;
import java.util.UUID;

public interface SimpleService <T> {
    T save(T simpleEntity);

    T findById(UUID uuid);

    List<T> findAll();

    Boolean delete(UUID uuid);
}
