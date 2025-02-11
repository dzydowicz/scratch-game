package com.dzydowicz.scratchgame.reward;

import com.dzydowicz.scratchgame.config.dto.ConfigDTO;
import com.dzydowicz.scratchgame.config.dto.ConfigSymbolDTO;
import java.util.Map;

class RewardCalculatorTotalRewardCalculator {
    private static final String MULTIPLY_REWARD = "multiply_reward";
    private static final String EXTRA_BONUS = "extra_bonus";

    public double computeTotalReward(Map<String, Map<String, RewardCalculatorWinCombinationRecordDTO>> winComboRecords,
                                     ConfigDTO config, int betAmount) {
        double totalReward = 0.0;
        for (Map.Entry<String, Map<String, RewardCalculatorWinCombinationRecordDTO>> entry : winComboRecords.entrySet()) {
            String symbol = entry.getKey();
            double comboMultiplier = 1.0;

            for (RewardCalculatorWinCombinationRecordDTO record : entry.getValue().values()) {
                comboMultiplier *= record.getRule().getRewardMultiplier();
            }

            ConfigSymbolDTO symbolConfig = config.getSymbols().get(symbol);
            if (symbolConfig != null) {
                double base = betAmount * symbolConfig.getRewardMultiplier();
                totalReward += base * comboMultiplier;
            }
        }

        return totalReward;
    }

    public double applyBonusEffect(double totalReward, String appliedBonusSymbol, ConfigDTO config) {
        if (totalReward > 0 && appliedBonusSymbol != null) {
            ConfigSymbolDTO bonusCfg = config.getSymbols().get(appliedBonusSymbol);

            if (bonusCfg != null && bonusCfg.getImpact() != null) {
                if (MULTIPLY_REWARD.equalsIgnoreCase(bonusCfg.getImpact())) {
                    return totalReward * bonusCfg.getRewardMultiplier();
                } else if (EXTRA_BONUS.equalsIgnoreCase(bonusCfg.getImpact())) {
                    return totalReward + bonusCfg.getExtra();
                }
            }
        }

        return totalReward;
    }
}
