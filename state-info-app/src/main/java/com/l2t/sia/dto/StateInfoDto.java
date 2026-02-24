package com.l2t.sia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StateInfoDto {

    private Integer id;
    private String state;
    private String capital;
    private String region;
    private String content;
}
