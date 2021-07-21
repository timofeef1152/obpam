package com.obpam.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Getter
@Setter
public class UserDto {
    @Null(message = "Id must be null")
    private Long id;
    @NotBlank(message = "Name couldn't be blank")
    private String name;
}
