package com.bunyasan.test.customer.model.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPatch {
    @Size(max = 100, message = "First name must be less than 100 characters")
    private String firstname;
    @Size(max = 100, message = "Last name must be less than 100 characters")
    private String lastname;
    private Date customerDate;
    private Boolean isVip;
    @Pattern(regexp = "\\d{3}", message = "Status code must be 3 digitS")
    private String statusCode;
}
