package com.dzydowicz.scratchgame.config.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import java.util.List;

@Data
public class ConfigProbabilitiesDTO {
    @SerializedName("standard_symbols")
    private List<ConfigStandardProbabilityDTO> standardSymbols;

    @SerializedName("bonus_symbols")
    private ConfigBonusProbabilityDTO bonusSymbols;
}