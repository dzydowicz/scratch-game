package com.dzydowicz.scratchgame.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Cell {
    private String symbol;
    private boolean bonus;
}