package com.dzydowicz.scratchgame.config.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigDTO {
    private Integer columns;
    private Integer rows;
    private Map<String, ConfigSymbolDTO> symbols;
    private ConfigProbabilitiesDTO probabilities;

    @SerializedName("win_combinations")
    private Map<String, ConfigWinCombinationDTO> winCombinations;
}
