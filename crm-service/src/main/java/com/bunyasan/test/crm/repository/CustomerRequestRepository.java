package com.bunyasan.test.crm.repository;

import com.bunyasan.test.crm.model.customerrequest.CustomerRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRequestRepository extends JpaRepository<CustomerRequest, Long> {
}
