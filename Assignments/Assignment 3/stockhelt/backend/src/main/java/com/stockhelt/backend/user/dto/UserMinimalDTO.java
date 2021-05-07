package com.stockhelt.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserMinimalDTO {
    private Long id;
    private String username;
}
