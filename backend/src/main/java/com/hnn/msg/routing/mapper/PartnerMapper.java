package com.hnn.msg.routing.mapper;

// src/main/java/com/example/mqconfig/mapper/PartnerMapper.java
import com.hnn.msg.routing.dto.PartnerDTO;
import com.hnn.msg.routing.model.Partner;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PartnerMapper {

    PartnerMapper INSTANCE = Mappers.getMapper(PartnerMapper.class);

    PartnerDTO toDto(Partner partner);

    Partner toEntity(PartnerDTO partnerDTO);

    void updateEntityFromDto(PartnerDTO partnerDTO, @MappingTarget Partner partner);
}
