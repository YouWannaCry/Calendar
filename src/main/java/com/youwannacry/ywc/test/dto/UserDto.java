package com.youwannacry.ywc.test.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Integer id;
    private String nombre;
    private String usuario;
    private String password;
    private Integer access;
    private List<ErrorDto> errors;

}
