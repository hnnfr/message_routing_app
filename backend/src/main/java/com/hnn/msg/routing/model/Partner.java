package com.hnn.msg.routing.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "partners")
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Alias is required")
    @Size(max = 50)
    @Column(unique = true)
    private String alias;

    @NotBlank(message = "Type is required")
    @Size(max = 50)
    private String type;

    @NotNull(message = "Direction is required")
    @Enumerated(EnumType.STRING)
    private Direction direction;

    @Size(max = 100)
    private String application;

    @Enumerated(EnumType.STRING)
    private FlowType processedFlowType;

    @NotBlank(message = "Description is required")
    @Size(max = 500)
    private String description;

    // Enums
    public enum Direction {
        INBOUND,
        OUTBOUND
    }

    public enum FlowType {
        MESSAGE,
        ALERTING,
        NOTIFICATION
    }

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

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public FlowType getProcessedFlowType() {
        return processedFlowType;
    }

    public void setProcessedFlowType(FlowType processedFlowType) {
        this.processedFlowType = processedFlowType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
