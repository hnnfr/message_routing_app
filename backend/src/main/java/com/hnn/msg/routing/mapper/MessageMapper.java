package com.hnn.msg.routing.mapper;

import com.hnn.msg.routing.dto.MessageDto;
import com.hnn.msg.routing.model.MqMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageDto messageToDto(MqMessage message);
}