package com.hnn.msg.routing.controller;

import com.hnn.msg.routing.dto.PartnerDTO;
import com.hnn.msg.routing.service.PartnerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/partners")
public class PartnerController {

    private final PartnerService partnerService;

    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @PostMapping
    public ResponseEntity<PartnerDTO> createPartner(@Valid @RequestBody PartnerDTO partnerDTO) {
        PartnerDTO createdPartner = partnerService.createPartner(partnerDTO);
        return new ResponseEntity<>(createdPartner, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PartnerDTO>> getAllPartners() {
        List<PartnerDTO> partners = partnerService.getAllPartners();
        return ResponseEntity.ok(partners);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartnerDTO> getPartnerById(@PathVariable Long id) {
        PartnerDTO partner = partnerService.getPartnerById(id);
        return ResponseEntity.ok(partner);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PartnerDTO> updatePartner(
            @PathVariable Long id,
            @Valid @RequestBody PartnerDTO partnerDTO) {
        PartnerDTO updatedPartner = partnerService.updatePartner(id, partnerDTO);
        return ResponseEntity.ok(updatedPartner);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartner(@PathVariable Long id) {
        partnerService.deletePartner(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<PartnerDTO>> searchByAlias(
            @RequestParam(required = false) String alias) {
        List<PartnerDTO> partners = partnerService.searchByAlias(alias != null ? alias : "");
        return ResponseEntity.ok(partners);
    }
}
