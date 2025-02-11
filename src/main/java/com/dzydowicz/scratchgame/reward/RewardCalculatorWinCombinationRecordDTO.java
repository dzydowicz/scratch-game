package com.dzydowicz.scratchgame.reward;

import com.dzydowicz.scratchgame.config.dto.ConfigWinCombinationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class RewardCalculatorWinCombinationRecordDTO {
    private String name;
    private ConfigWinCombinationDTO rule;
}