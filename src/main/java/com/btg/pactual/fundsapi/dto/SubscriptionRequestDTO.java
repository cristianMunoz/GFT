package com.btg.pactual.fundsapi.dto;

import jakarta.validation.constraints.NotBlank;

public class SubscriptionRequestDTO {

    @NotBlank(message = "El ID del fondo no puede estar vacío")
    private String fundId;

    public String getFundId() {
        return fundId;
    }

    public void setFundId(String fundId) {
        this.fundId = fundId;
    }
}