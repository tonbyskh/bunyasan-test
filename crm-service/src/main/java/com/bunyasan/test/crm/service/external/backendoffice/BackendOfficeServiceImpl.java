package com.bunyasan.test.crm.service.external.backendoffice;

import com.bunyasan.test.crm.service.external.ApiConfig;
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
public class BackendOfficeServiceImpl implements BackendOfficeService {
    private final ApiConfig apiConfig;
    public void forwardToBackendOffice(Long customerRequestId) {
        log.debug("Call GET {}", apiConfig.getForwardToBackendOfficeUrl());
        Unirest.get(apiConfig.getForwardToBackendOfficeUrl())
                .routeParam("customerRequestId", String.valueOf(customerRequestId))
                .asObject(Void.class)
                .ifFailure(
                        res -> {
                            throw new UnirestException("Error to call GET " + apiConfig.getForwardToBackendOfficeUrl() +" with status code "+ res.getStatus());
                        }
                );
    }
}
