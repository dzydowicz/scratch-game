package com.dzydowicz.scratchgame.config.dto;

import lombok.Data;
import java.util.Map;

@Data
public class ConfigStandardProbabilityDTO {
    private int row;
    private int column;
    private Map<String, Integer> symbols;
}
