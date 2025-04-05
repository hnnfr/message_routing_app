package com.hnn.msg.routing.dto;

import com.hnn.msg.routing.model.Partner;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PartnerDTO {
    private Long id;

    @NotBlank(message = "Alias is required")
    @Size(max = 50)
    private String alias;

    @NotBlank(message = "Type is required")
    @Size(max = 50)
    private String type;

    @NotNull(message = "Direction is required")
    private Partner.Direction direction;

    @Size(max = 100)
    private String application;

    private Partner.FlowType processedFlowType;

    @NotBlank(message = "Description is required")
    @Size(max = 500)
    private String description;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Partner.Direction getDirection() {
        return direction;
    }

    public void setDirection(Partner.Direction direction) {
        this.direction = direction;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public Partner.FlowType getProcessedFlowType() {
        return processedFlowType;
    }

    public void setProcessedFlowType(Partner.FlowType processedFlowType) {
        this.processedFlowType = processedFlowType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
