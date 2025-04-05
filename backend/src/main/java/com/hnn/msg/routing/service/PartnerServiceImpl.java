package com.hnn.msg.routing.service;


import com.hnn.msg.routing.dto.PartnerDTO;
import com.hnn.msg.routing.mapper.PartnerMapper;
import com.hnn.msg.routing.model.Partner;
import com.hnn.msg.routing.repository.PartnerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;
    private final PartnerMapper partnerMapper;

    public PartnerServiceImpl(PartnerRepository partnerRepository, PartnerMapper partnerMapper) {
        this.partnerRepository = partnerRepository;
        this.partnerMapper = partnerMapper;
    }

    public PartnerDTO createPartner(PartnerDTO partnerDTO) {
        Partner partner = partnerMapper.toEntity(partnerDTO);
        Partner savedPartner = partnerRepository.save(partner);
        return partnerMapper.toDto(savedPartner);
    }

    public List<PartnerDTO> getAllPartners() {
        return partnerRepository.findAll().stream()
                .map(partnerMapper::toDto)
                .toList();
    }

    public PartnerDTO getPartnerById(Long id) {
        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Partner not found with id: " + id));
        return partnerMapper.toDto(partner);
    }

    public PartnerDTO updatePartner(Long id, PartnerDTO partnerDTO) {
        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Partner not found with id: " + id));
        partnerMapper.updateEntityFromDto(partnerDTO, partner);
        Partner updatedPartner = partnerRepository.save(partner);
        return partnerMapper.toDto(updatedPartner);
    }

    public void deletePartner(Long id) {
        partnerRepository.deleteById(id);
    }

    public List<PartnerDTO> searchByAlias(String alias) {
        List<Partner> partners = partnerRepository.findByAliasContainingIgnoreCase(alias);
        return partners.stream()
                .map(partnerMapper::toDto)
                .toList();
    }
}
