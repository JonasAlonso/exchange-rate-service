package com.business.keeper.exchangerateservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ErrorDto {

    private String status;

    private String title;

    private String description;
}
