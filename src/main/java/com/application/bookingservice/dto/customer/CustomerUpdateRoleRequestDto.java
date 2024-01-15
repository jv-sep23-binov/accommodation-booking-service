package com.application.bookingservice.dto.customer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import lombok.Data;
import lombok.NonNull;

@Data
public class CustomerUpdateRoleRequestDto {
    @NonNull
    private Set<Long> roleIds;

    @JsonCreator
    public CustomerUpdateRoleRequestDto(@JsonProperty("roleIds") @NonNull Set<Long> roleIds) {
        this.roleIds = roleIds;
    }
}
