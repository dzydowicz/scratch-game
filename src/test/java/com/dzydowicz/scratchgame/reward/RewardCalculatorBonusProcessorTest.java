package com.dzydowicz.scratchgame.reward;

import com.dzydowicz.scratchgame.config.dto.ConfigDTO;
import com.dzydowicz.scratchgame.config.dto.ConfigSymbolDTO;
import com.dzydowicz.scratchgame.dto.Cell;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RewardCalculatorBonusProcessorTest {
    private static final String BONUS_SYMBOL = "BONUS";
    private static final String A_SYMBOL = "A";

    private final RewardCalculatorBonusProcessor processor = new RewardCalculatorBonusProcessor();

    @Test
    public void testSelectBonusSymbol() {
        ConfigDTO config = initConfigDTO();
        Cell[][] matrix = initMatrix();

        String selected = processor.selectBonusSymbol(config, matrix, 2, 2, 100.0);
        assertEquals(BONUS_SYMBOL, selected);
    }

    @Test
    public void testSelectBonusSymbol_NoBonusIfZeroReward() {
        ConfigDTO config = initConfigDTO();
        Cell[][] matrix = initMatrix();

        String selected = processor.selectBonusSymbol(config, matrix, 2, 2, 0.0);
        assertNull(selected);
    }

    private ConfigDTO initConfigDTO() {
        Map<String, ConfigSymbolDTO> symbols = Maps.newHashMap();

        ConfigSymbolDTO bonusSymbol = new ConfigSymbolDTO();
        bonusSymbol.setImpact("multiply_reward");
        bonusSymbol.setRewardMultiplier(10.0);
        symbols.put(BONUS_SYMBOL, bonusSymbol);

        return ConfigDTO.builder()
                .symbols(symbols)
                .build();
    }

    private Cell[][] initMatrix() {
        return new Cell[][] {
                { new Cell(A_SYMBOL, false), new Cell(A_SYMBOL, false) },
                { new Cell(BONUS_SYMBOL, true), new Cell(A_SYMBOL, false) }
        };
    }
}
