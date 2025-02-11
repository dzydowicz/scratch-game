package com.dzydowicz.scratchgame.reward;

import com.dzydowicz.scratchgame.config.dto.ConfigDTO;
import com.dzydowicz.scratchgame.dto.Cell;
import com.dzydowicz.scratchgame.dto.Result;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
class RewardCalculator implements IRewardCalculator {
    private final RewardCalculatorWinCombinationProcessor winCombinationProcessor;
    private final RewardCalculatorBonusProcessor bonusProcessor;
    private final RewardCalculatorTotalRewardCalculator totalRewardCalculator;

    @Override
    public Result calculateReward(ConfigDTO config, Cell[][] matrix, int betAmount, int rows, int cols) {
        Map<String, Integer> symbolCounts = RewardCalculatorSymbolCounter.count(matrix, rows, cols);

        Map<String, Map<String, RewardCalculatorWinCombinationRecordDTO>> winComboRecords =
                winCombinationProcessor.process(config.getWinCombinations(), symbolCounts, matrix, rows, cols);

        double totalReward = totalRewardCalculator.computeTotalReward(winComboRecords, config, betAmount);

        String appliedBonusSymbol = bonusProcessor.selectBonusSymbol(config, matrix, rows, cols, totalReward);
        double finalReward = totalRewardCalculator.applyBonusEffect(totalReward, appliedBonusSymbol, config);

        return Result.builder()
                .reward((int) Math.round(finalReward))
                .appliedWinningCombinations(RewardCalculatorWinCombinationMapper.toAppliedWinCombinationNames(winComboRecords))
                .appliedBonusSymbol(appliedBonusSymbol)
                .build();
    }
}
