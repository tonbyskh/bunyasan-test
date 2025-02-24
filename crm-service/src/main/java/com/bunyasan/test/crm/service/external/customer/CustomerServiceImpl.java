package com.bunyasan.test.crm.service.external.customer;

import com.bunyasan.test.crm.model.customer.CustomerInfo;
import com.bunyasan.test.crm.service.external.ApiConfig;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Profile("!local")
public class CustomerServiceImpl implements CustomerService {
    private final ApiConfig apiConfig;

    @Override
    public CustomerInfo getCustomer(String phone) {
        log.debug("Call GET {}", apiConfig.getCustomerByPhoneUrl());
        HttpResponse<CustomerInfo> response = Unirest.get(apiConfig.getCustomerByPhoneUrl())
                .queryString("phone", phone)
                .asObject(CustomerInfo.class)
                .ifFailure(
                        res -> {
                            throw new UnirestException("Error to call GET " + apiConfig.getCustomerByPhoneUrl() +" with status code "+ res.getStatus());
                        }
                );
        return response.getBody();
    }

    @Override
    public CustomerInfo getCustomerById(Long customerId) {
        log.debug("Call GET {}", apiConfig.getCustomerByIdUrl());
        HttpResponse<CustomerInfo> response = Unirest.get(apiConfig.getCustomerByIdUrl())
                .routeParam("customerId", String.valueOf(customerId))
                .asObject(CustomerInfo.class)
                .ifFailure(
                        res -> {
                            throw new UnirestException("Error to call GET " + apiConfig.getCustomerByIdUrl() +" with status code "+ res.getStatus());
                        }
                );
        return response.getBody();
    }

    @Override
    public void verifyCustomer(Long customerId) {
        log.debug("Call GET {}", apiConfig.getVerifyCustomerUrl());
        Unirest.get(apiConfig.getVerifyCustomerUrl())
                .routeParam("customerId", String.valueOf(customerId))
                .asObject(CustomerInfo.class)
                .ifFailure(
                        res -> {
                            throw new UnirestException("Error to call GET " + apiConfig.getVerifyCustomerUrl() +" with status code "+ res.getStatus());
                        }
                );
    }
}
