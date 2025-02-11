package com.dzydowicz.scratchgame.config.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ConfigSymbolDTO {
    @SerializedName("reward_multiplier")
    private double rewardMultiplier;

    private String type;
    private Double extra;
    private String impact;
}
