package com.hnn.msg.routing.controller;

import com.hnn.msg.routing.dto.PartnerDTO;
import com.hnn.msg.routing.service.PartnerService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PartnerControllerTest {

    @Mock
    private PartnerService partnerService;

    @InjectMocks
    private PartnerController partnerController;

    private MockMvc mockMvc;

    private void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(partnerController).build();
    }

    @Test
    void createPartner_ShouldReturnCreated() throws Exception {
        setup();
        PartnerDTO responseDto = new PartnerDTO();
        responseDto.setId(1L);

        when(partnerService.createPartner(any(PartnerDTO.class))).thenReturn(responseDto);

        mockMvc.perform(post("/partners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Partner\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));

        verify(partnerService).createPartner(any(PartnerDTO.class));
    }

    @Test
    void getAllPartners_ShouldReturnPartnerList() throws Exception {
        setup();
        PartnerDTO partnerDto = new PartnerDTO();
        List<PartnerDTO> partners = Collections.singletonList(partnerDto);

        when(partnerService.getAllPartners()).thenReturn(partners);

        mockMvc.perform(get("/partners"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));

        verify(partnerService).getAllPartners();
    }

    @Test
    void getPartnerById_WhenExists_ShouldReturnPartner() throws Exception {
        setup();
        PartnerDTO partnerDto = new PartnerDTO();
        partnerDto.setId(1L);

        when(partnerService.getPartnerById(1L)).thenReturn(partnerDto);

        mockMvc.perform(get("/partners/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(partnerService).getPartnerById(1L);
    }

    @Test
    void updatePartner_ShouldReturnUpdatedPartner() throws Exception {
        setup();
        PartnerDTO responseDto = new PartnerDTO();
        responseDto.setId(1L);

        when(partnerService.updatePartner(eq(1L), any(PartnerDTO.class))).thenReturn(responseDto);

        mockMvc.perform(put("/partners/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Partner\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(partnerService).updatePartner(eq(1L), any(PartnerDTO.class));
    }

    @Test
    void deletePartner_ShouldReturnNoContent() throws Exception {
        setup();
        doNothing().when(partnerService).deletePartner(1L);

        mockMvc.perform(delete("/partners/1"))
                .andExpect(status().isNoContent());

        verify(partnerService).deletePartner(1L);
    }

    @Test
    void searchByAlias_WithParameter_ShouldReturnResults() throws Exception {
        setup();
        PartnerDTO partnerDto = new PartnerDTO();
        List<PartnerDTO> partners = Collections.singletonList(partnerDto);

        when(partnerService.searchByAlias("test")).thenReturn(partners);

        mockMvc.perform(get("/partners/search")
                        .param("alias", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(partnerService).searchByAlias("test");
    }

    @Test
    void searchByAlias_WithoutParameter_ShouldUseEmptyString() throws Exception {
        setup();
        when(partnerService.searchByAlias("")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/partners/search"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(partnerService).searchByAlias("");
    }
}
