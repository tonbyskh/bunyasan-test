package com.bunyasan.test.customer.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public interface CustomerSalesRankResponse{
    Integer getRank();
    Long getCustomerId();
    @JsonIgnore
    String getFirstName();
    @JsonIgnore
    String getLastName();
    @JsonProperty("customerName")
    public default String getCustomerName() {
        return getFirstName() + " " + getLastName();
    }
    Date getCustomerDate();
    Boolean getIsVip();
    String getStatusCode();
    Double getTotal();

}
