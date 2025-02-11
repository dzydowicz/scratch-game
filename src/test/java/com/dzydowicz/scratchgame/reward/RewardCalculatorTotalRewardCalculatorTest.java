package com.dzydowicz.scratchgame.reward;

import com.dzydowicz.scratchgame.config.dto.ConfigDTO;
import com.dzydowicz.scratchgame.config.dto.ConfigSymbolDTO;
import com.dzydowicz.scratchgame.config.dto.ConfigWinCombinationDTO;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RewardCalculatorTotalRewardCalculatorTest {
    private final RewardCalculatorTotalRewardCalculator rewardCalculator = new RewardCalculatorTotalRewardCalculator();

    @Test
    public void testComputeTotalReward() {
        Map<String, Map<String, RewardCalculatorWinCombinationRecordDTO>> winComboRecords = initWinComboMap();

        Map<String, ConfigSymbolDTO> symbols = Maps.newHashMap();
        ConfigSymbolDTO symbolA = new ConfigSymbolDTO();
        symbolA.setRewardMultiplier(5.0);
        symbols.put("A", symbolA);

        ConfigDTO config = ConfigDTO.builder()
                .symbols(symbols)
                .build();

        int betAmount = 100;
        double totalReward = rewardCalculator.computeTotalReward(winComboRecords, config, betAmount);

        assertEquals(1000.0, totalReward, 0.001);
    }

    @Test
    public void testApplyBonusEffect_Multiply() {
        Map<String, ConfigSymbolDTO> symbols = Maps.newHashMap();
        ConfigSymbolDTO bonusSymbol = new ConfigSymbolDTO();
        bonusSymbol.setImpact("multiply_reward");
        bonusSymbol.setRewardMultiplier(10.0);
        symbols.put("BONUS", bonusSymbol);

        ConfigDTO config = ConfigDTO.builder()
                .symbols(symbols)
                .build();

        double baseReward = 1000.0;
        double finalReward = rewardCalculator.applyBonusEffect(baseReward, "BONUS", config);

        assertEquals(10000.0, finalReward, 0.001);
    }

    @Test
    public void testApplyBonusEffect_ExtraBonus() {
        Map<String, ConfigSymbolDTO> symbols = Maps.newHashMap();
        ConfigSymbolDTO bonusSymbol = new ConfigSymbolDTO();
        bonusSymbol.setImpact("extra_bonus");
        bonusSymbol.setExtra(500.0);
        symbols.put("BONUS", bonusSymbol);

        ConfigDTO config = ConfigDTO.builder()
                .symbols(symbols)
                .build();

        double baseReward = 1000.0;
        double finalReward = rewardCalculator.applyBonusEffect(baseReward, "BONUS", config);

        assertEquals(1500.0, finalReward, 0.001);
    }

    private static Map<String, Map<String, RewardCalculatorWinCombinationRecordDTO>> initWinComboMap() {
        Map<String, Map<String, RewardCalculatorWinCombinationRecordDTO>> winComboRecords = Maps.newHashMap();
        Map<String, RewardCalculatorWinCombinationRecordDTO> groupMap = Maps.newHashMap();

        ConfigWinCombinationDTO rule = new ConfigWinCombinationDTO();
        rule.setRewardMultiplier(2.0);
        rule.setGroup("standard");
        RewardCalculatorWinCombinationRecordDTO record = new RewardCalculatorWinCombinationRecordDTO("win1", rule);
        groupMap.put("standard", record);
        winComboRecords.put("A", groupMap);
        return winComboRecords;
    }

}
