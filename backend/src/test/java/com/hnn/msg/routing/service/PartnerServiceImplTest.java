package com.hnn.msg.routing.service;

import com.hnn.msg.routing.dto.PartnerDTO;
import com.hnn.msg.routing.mapper.PartnerMapper;
import com.hnn.msg.routing.model.Partner;
import com.hnn.msg.routing.repository.PartnerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PartnerServiceImplTest {

    @Mock
    private PartnerRepository partnerRepository;

    @Mock
    private PartnerMapper partnerMapper;

    @InjectMocks
    private PartnerServiceImpl partnerService;

    @Test
    void createPartner_ShouldReturnSavedPartnerDTO() {
        // Arrange
        PartnerDTO inputDto = new PartnerDTO();
        Partner entity = new Partner();
        Partner savedEntity = new Partner();
        PartnerDTO expectedDto = new PartnerDTO();

        when(partnerMapper.toEntity(inputDto)).thenReturn(entity);
        when(partnerRepository.save(entity)).thenReturn(savedEntity);
        when(partnerMapper.toDto(savedEntity)).thenReturn(expectedDto);

        // Act
        PartnerDTO result = partnerService.createPartner(inputDto);

        // Assert
        assertEquals(expectedDto, result);
        verify(partnerMapper).toEntity(inputDto);
        verify(partnerRepository).save(entity);
        verify(partnerMapper).toDto(savedEntity);
    }

    @Test
    void getAllPartners_ShouldReturnListOfDTOs() {
        // Arrange
        Partner entity = new Partner();
        PartnerDTO dto = new PartnerDTO();

        when(partnerRepository.findAll()).thenReturn(Collections.singletonList(entity));
        when(partnerMapper.toDto(entity)).thenReturn(dto);

        // Act
        List<PartnerDTO> result = partnerService.getAllPartners();

        // Assert
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
        verify(partnerRepository).findAll();
        verify(partnerMapper).toDto(entity);
    }

    @Test
    void getPartnerById_WhenExists_ShouldReturnDTO() {
        // Arrange
        Long id = 1L;
        Partner entity = new Partner();
        PartnerDTO expectedDto = new PartnerDTO();

        when(partnerRepository.findById(id)).thenReturn(Optional.of(entity));
        when(partnerMapper.toDto(entity)).thenReturn(expectedDto);

        // Act
        PartnerDTO result = partnerService.getPartnerById(id);

        // Assert
        assertEquals(expectedDto, result);
        verify(partnerRepository).findById(id);
        verify(partnerMapper).toDto(entity);
    }

    @Test
    void getPartnerById_WhenNotExists_ShouldThrowException() {
        // Arrange
        Long id = 99L;
        when(partnerRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () ->
                partnerService.getPartnerById(id)
        );
        verify(partnerRepository).findById(id);
        verifyNoInteractions(partnerMapper);
    }

    @Test
    void updatePartner_WhenExists_ShouldReturnUpdatedDTO() {
        // Arrange
        Long id = 1L;
        PartnerDTO inputDto = new PartnerDTO();
        Partner existingEntity = new Partner();
        Partner updatedEntity = new Partner();
        PartnerDTO expectedDto = new PartnerDTO();

        when(partnerRepository.findById(id)).thenReturn(Optional.of(existingEntity));
        doNothing().when(partnerMapper).updateEntityFromDto(inputDto, existingEntity);
        when(partnerRepository.save(existingEntity)).thenReturn(updatedEntity);
        when(partnerMapper.toDto(updatedEntity)).thenReturn(expectedDto);

        // Act
        PartnerDTO result = partnerService.updatePartner(id, inputDto);

        // Assert
        assertEquals(expectedDto, result);
        verify(partnerRepository).findById(id);
        verify(partnerMapper).updateEntityFromDto(inputDto, existingEntity);
        verify(partnerRepository).save(existingEntity);
        verify(partnerMapper).toDto(updatedEntity);
    }

    @Test
    void updatePartner_WhenNotExists_ShouldThrowException() {
        // Arrange
        Long id = 99L;
        PartnerDTO inputDto = new PartnerDTO();
        when(partnerRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () ->
                partnerService.updatePartner(id, inputDto)
        );
        verify(partnerRepository).findById(id);
        verifyNoMoreInteractions(partnerRepository);
        verifyNoInteractions(partnerMapper);
    }

    @Test
    void deletePartner_ShouldCallRepositoryDelete() {
        // Arrange
        Long id = 1L;
        doNothing().when(partnerRepository).deleteById(id);

        // Act
        partnerService.deletePartner(id);

        // Assert
        verify(partnerRepository).deleteById(id);
    }

    @Test
    void searchByAlias_ShouldReturnMatchingDTOs() {
        // Arrange
        String alias = "test";
        Partner entity = new Partner();
        PartnerDTO dto = new PartnerDTO();

        when(partnerRepository.findByAliasContainingIgnoreCase(alias))
                .thenReturn(Collections.singletonList(entity));
        when(partnerMapper.toDto(entity)).thenReturn(dto);

        // Act
        List<PartnerDTO> result = partnerService.searchByAlias(alias);

        // Assert
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
        verify(partnerRepository).findByAliasContainingIgnoreCase(alias);
        verify(partnerMapper).toDto(entity);
    }
}