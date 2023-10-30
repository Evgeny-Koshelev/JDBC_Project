package org.example.servlet.mapper;


import org.example.model.Event;
import org.example.servlet.dto.EventDto;

public class EventDtoMapperImpl implements SimpleDtoMapper<Event, EventDto> {
    @Override
    public Event toEntity(EventDto incomingDto) {
        Event event = new Event();
        event.setId(incomingDto.getId());
        event.setUserId(incomingDto.getUserId());
        event.setVacancyId(incomingDto.getVacancyId());
        event.setBeginDate(incomingDto.getBeginDate());
        event.setNotes(incomingDto.getNotes());
        event.setIsCompleted(incomingDto.getIsCompleted());
        return event;
    }

    @Override
    public EventDto toDto(Event simpleEntity) {
        EventDto eventDto = new EventDto();
        eventDto.setId(simpleEntity.getId());
        eventDto.setUserId(simpleEntity.getUserId());
        eventDto.setVacancyId(simpleEntity.getVacancyId());
        eventDto.setBeginDate(simpleEntity.getBeginDate());
        eventDto.setNotes(simpleEntity.getNotes());
        eventDto.setIsCompleted(simpleEntity.getIsCompleted());
        return eventDto;
    }
}
