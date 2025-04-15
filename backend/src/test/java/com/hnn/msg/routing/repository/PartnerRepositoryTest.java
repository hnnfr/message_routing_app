package com.hnn.msg.routing.repository;

import com.hnn.msg.routing.model.Partner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class PartnerRepositoryTest {

    @Autowired
    private PartnerRepository partnerRepository;

    @Test
    void shouldSaveAndRetrievePartner() {
        Partner partner = getPartner();
        Partner saved = partnerRepository.save(partner);

        assertThat(partnerRepository.findById(saved.getId()))
                .isPresent();
    }

    private Partner getPartner() {
        Partner partner = new Partner();
        partner.setAlias("test-alias");
        partner.setDescription("test-description");
        partner.setDirection(Partner.Direction.INBOUND);
        partner.setProcessedFlowType(Partner.FlowType.MESSAGE);
        partner.setType("test-type");
        return partner;
    }
}
