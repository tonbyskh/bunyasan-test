package com.bunyasan.test.customer.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sales")
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "sales_id")
    private Long id;

    @NotNull(message = "Please enter sale amount")
    @Column(name = "sale_amount")
    private Double saleAmount;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sale_date", columnDefinition = "datetime", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date saleDate;

    @ManyToOne
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "FK_sales_customer"))
    @JsonIgnore
    private Customer customer;
}
