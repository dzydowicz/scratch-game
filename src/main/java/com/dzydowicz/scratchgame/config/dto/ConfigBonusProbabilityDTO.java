package com.dzydowicz.scratchgame.config.dto;

import lombok.Data;
import java.util.Map;

@Data
public class ConfigBonusProbabilityDTO {
    private Map<String, Integer> symbols;
}