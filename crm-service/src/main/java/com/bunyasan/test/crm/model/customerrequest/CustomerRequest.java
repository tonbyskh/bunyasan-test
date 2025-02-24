package com.bunyasan.test.crm.model.customerrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Accessors(chain = true)
@Table(name = "customer_request")
public class CustomerRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "customer_request_id")
    private Long id;

    @Column(name = "customer_id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long customerId;

    @Pattern(regexp = "^\\d{3}$", message = "TypeCode must be number 3 digit")
    @Schema(example = "000")
    @Column(name = "type_code", length = 3, nullable = false)
    private String typeCode;

    @Size(max = 500, message = "Description must be less than 100 characters")
    @Column(name = "description", length = 500)
    private String description;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "status", length = 20)
    private String status;

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
}
