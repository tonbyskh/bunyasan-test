package com.bunyasan.test.crm.model.customerrequest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestPatch {
    @Schema(maxLength = 500)
    private String description;
    @Schema(maxLength = 20)
    @Pattern(regexp = "^(In-progress|Completed|Canceled)$", message = "Status must be In-progress, Completed or Canceled")
    private String status;
}
