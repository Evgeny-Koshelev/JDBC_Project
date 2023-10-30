package org.example.servlet.mapper;

import org.example.model.Status;
import org.example.servlet.dto.StatusDto;

public class StatusDtoMapperImpl implements SimpleDtoMapper<Status, StatusDto> {
    @Override
    public Status toEntity(StatusDto incomingDto) {
        Status status = new Status();
        status.setId(incomingDto.getId());
        status.setUserId(incomingDto.getUserId());
        status.setNameStatus(incomingDto.getNameStatus());
        status.setOrderNum(incomingDto.getOrderNum());
        return status;
    }

    @Override
    public StatusDto toDto(Status simpleEntity) {
        StatusDto statusDto = new StatusDto();
        statusDto.setId(simpleEntity.getId());
        statusDto.setVacancies(simpleEntity.getVacancies());
        statusDto.setUserId(simpleEntity.getUserId());
        statusDto.setNameStatus(simpleEntity.getNameStatus());
        statusDto.setOrderNum(simpleEntity.getOrderNum());
        return statusDto;
    }
}
