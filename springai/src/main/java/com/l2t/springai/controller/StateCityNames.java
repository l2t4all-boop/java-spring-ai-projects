package com.l2t.springai.controller;

import lombok.Data;

import java.util.List;

@Data
public class StateCityNames {
    private String stateName;
    private List<String> cityNames;
}
