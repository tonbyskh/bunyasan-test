package com.bunyasan.test.customer.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "customer_id")
    private Long id;

    @NotBlank(message = "Please enter first name")
    @Size(max = 100, message = "First name must be less than 100 characters")
    @Column(name = "first_name", length = 100, nullable = false)
    private String firstname;

    @NotBlank(message = "Please enter last name")
    @Size(max = 100, message = "Last name must be less than 100 characters")
    @Column(name = "last_name", length = 100, nullable = false)
    private String lastname;

    @NotNull(message = "Please enter customer date")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "customer_date", columnDefinition = "datetime", nullable = false)
    private Date customerDate;

    @NotNull(message = "Please enter is vip")
    @Column(name = "is_vip", nullable = false)
    private Boolean isVip;

    @NotBlank(message = "Please enter status code")
    @Pattern(regexp = "\\d{3}", message = "Status code must be 3 digitS")
    @Column(name = "status_code", length = 3, nullable = false)
    private String statusCode;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", columnDefinition = "datetime", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createdOn;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_on", columnDefinition = "datetime", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date modifiedOn;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Sales> sales;
}
