package com.bunyasan.test.customer.repository;

import com.bunyasan.test.customer.model.entity.Sales;
import com.bunyasan.test.customer.model.response.CustomerSalesRankResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Long> {

    @Query(value = """
            WITH CustomerSales AS (
                SELECT
                    RANK() OVER (ORDER BY SUM(s.sale_amount) DESC) AS rank,
                    c.customer_id,
                    c.first_name,
                    c.last_name,
                    c.customer_date,
                    c.is_vip,
                    c.status_code,
                    SUM(s.sale_amount) AS total
                FROM customer c
                JOIN sales s ON c.customer_id = s.customer_id
                GROUP BY c.customer_id, c.first_name, c.last_name, c.customer_date, c.is_vip, c.status_code
            )
            SELECT * FROM CustomerSales WHERE rank <= 10;
            """, nativeQuery = true)
    List<CustomerSalesRankResponse> getCustomerSalesRank();
}
