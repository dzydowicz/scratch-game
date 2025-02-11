package com.dzydowicz.scratchgame.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private List<List<String>> matrix;
    private int reward;

    @SerializedName("applied_winning_combinations")
    private Map<String, List<String>> appliedWinningCombinations;

    @SerializedName("applied_bonus_symbol")
    private String appliedBonusSymbol;
}
