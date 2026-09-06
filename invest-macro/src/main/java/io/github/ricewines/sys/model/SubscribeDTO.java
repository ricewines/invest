package io.github.ricewines.sys.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SubscribeDTO {
    @NotBlank
    @Email
    private String email;
}