package com.bunyasan.test.crm.service.external;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
@ConfigurationProperties(prefix = "app.api.config")
public class ApiConfig {
    public String getCustomerByPhoneUrl() {
        return getCustomerByPhoneUrl;
    }
    public String getCustomerByIdUrl() {
        return getCustomerByIdUrl;
    }
    public String getVerifyCustomerUrl() {
        return getVerifyCustomerUrl;
    }
    public String getForwardToBackendOfficeUrl() { return getForwardToBackendOfficeUrl; }
    public String getAllCustomerBankAccountUrl() { return getAllCustomerBankAccountUrl; }
    public String getBankAccountDetailUrl() { return getBankAccountDetailUrl; }
    public String postPostAccountTransactionUrl() { return postAccountTransactionUrl; }

    private String getCustomerByPhoneUrl;
    private String getCustomerByIdUrl;
    private String getVerifyCustomerUrl;

    private String getForwardToBackendOfficeUrl;

    private String getAllCustomerBankAccountUrl;
    private String getBankAccountDetailUrl;
    private String postAccountTransactionUrl;



}
