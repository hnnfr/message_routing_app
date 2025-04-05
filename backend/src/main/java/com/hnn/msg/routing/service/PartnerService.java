package com.hnn.msg.routing.service;

import com.hnn.msg.routing.dto.PartnerDTO;

import java.util.List;

public interface PartnerService {
    PartnerDTO createPartner(PartnerDTO partnerDTO);
    List<PartnerDTO> getAllPartners();
    PartnerDTO getPartnerById(Long id);
    PartnerDTO updatePartner(Long id, PartnerDTO partnerDTO);
    void deletePartner(Long id);
    List<PartnerDTO> searchByAlias(String alias);
}
