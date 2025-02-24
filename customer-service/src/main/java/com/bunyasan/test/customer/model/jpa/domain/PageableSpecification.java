package com.bunyasan.test.customer.model.jpa.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@Setter
@Getter
public abstract class PageableSpecification<T> {
    @Getter
    @Setter
    public static class Pagination{
        @Schema(example = "0")
        @Min(value = 0L, message = "The value must not be less than zero")
        private Integer pageNumber = 0;

        @Schema(example = "10")
        @Min(value = 10L, message = "The minimum value is 10")
        private Integer pageSize = 10;

        @Valid
        private List<CustomOrder> orders ;


        public PageRequest toPageRequest(){
            if(pageSize > 500) pageSize = 500;
            if(orders !=null && !orders.isEmpty()){
                Sort sort =  getOrderBy(orders);
                return PageRequest.of(pageNumber, pageSize,sort);
            }
            return PageRequest.of(pageNumber, pageSize);
        }


        @Setter
        @Getter
        public static class CustomOrder {

            @Pattern(regexp = "^(ASC|DESC)$",message = "direction must be ASC or DESC")
            private String direction;

            private String property;

            public CustomOrder(@JsonProperty("direction") @NotNull String direction, @JsonProperty("property") @NotNull String property) {
                this.direction = direction;
                this.property = property;
            }

        }

        public Sort getOrderBy(List<CustomOrder> orderBy){
            Sort sort=  null;
            for (CustomOrder order : orders) {
                if (sort == null) {
                    sort = Sort.by(Sort.Direction.fromString(order.getDirection()), order.getProperty());
                } else {
                    sort = sort.and(Sort.by(Sort.Direction.fromString(order.getDirection()), order.getProperty()));
                }
            }
            return sort;
        }

    }

    @Valid
    protected Pagination pagination;

    public PageRequest toPageRequest(){
        if(pagination == null) pagination = new Pagination();
        return  pagination.toPageRequest();
    }
}
