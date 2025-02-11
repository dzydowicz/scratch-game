package com.dzydowicz.scratchgame.config.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import java.util.List;

@Data
public class ConfigWinCombinationDTO {
    @SerializedName("reward_multiplier")
    private double rewardMultiplier;

    private String when;
    private Integer count;
    private String group;

    @SerializedName("covered_areas")
    private List<List<String>> coveredAreas;
}