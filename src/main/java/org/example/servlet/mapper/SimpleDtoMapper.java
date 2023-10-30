package org.example.servlet.mapper;


public interface SimpleDtoMapper<T, K> {
    T toEntity (K incomingDto);

    K toDto(T simpleEntity);

}
