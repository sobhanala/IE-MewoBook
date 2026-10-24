package webapi.views.user;

import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public record SessionControl(
        @NotBlank UserView user,
        Date timestamp
) {}

