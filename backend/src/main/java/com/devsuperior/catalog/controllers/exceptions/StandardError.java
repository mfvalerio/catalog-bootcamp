package com.devsuperior.catalog.controllers.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class StandardError {
    private static final Long serialVersionUID = 1L;

    private LocalDateTime timestamp;

    private Integer status;

    private String error;

    private String message;

    private String path;
}
