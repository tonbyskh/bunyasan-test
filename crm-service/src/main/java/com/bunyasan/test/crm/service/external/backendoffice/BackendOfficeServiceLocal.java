package com.bunyasan.test.crm.service.external.backendoffice;

import com.bunyasan.test.crm.service.external.ApiConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Profile("local")
public class BackendOfficeServiceLocal implements BackendOfficeService {
    private final ApiConfig apiConfig;
    public void forwardToBackendOffice(Long customerRequestId) {
        log.info("Forward customer request {} to backend office", customerRequestId);
    }
}
