package com.dzydowicz.scratchgame.reward;

import com.dzydowicz.scratchgame.config.dto.ConfigDTO;
import com.dzydowicz.scratchgame.dto.Cell;
import com.dzydowicz.scratchgame.dto.Result;

public interface IRewardCalculator {
    Result calculateReward(ConfigDTO config, Cell[][] matrix, int betAmount, int rows, int cols);
}
