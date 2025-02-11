package com.dzydowicz.scratchgame.reward;

import com.dzydowicz.scratchgame.config.dto.ConfigWinCombinationDTO;
import com.dzydowicz.scratchgame.dto.Cell;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RewardCalculatorWinCombinationProcessorTest {
    private static final String A_SYMBOL = "A";

    private static final String SAME_SYMBOLS = "same_symbols";
    private static final String COMBO_WIN1 = "win1";

    private final RewardCalculatorWinCombinationProcessor processor = new RewardCalculatorWinCombinationProcessor();

    @Test
    public void testProcessSameSymbols() {
        Map<String, ConfigWinCombinationDTO> winCombos = initWinCombos();

        Map<String, Integer> symbolCounts = new HashMap<>();
        symbolCounts.put(A_SYMBOL, 3);

        Map<String, Map<String, RewardCalculatorWinCombinationRecordDTO>> result =
                processor.process(winCombos, symbolCounts, new Cell[1][1], 1, 1);

        assertTrue(result.containsKey(A_SYMBOL));

        Map<String, RewardCalculatorWinCombinationRecordDTO> groupMap = result.get(A_SYMBOL);
        assertTrue(groupMap.containsKey(SAME_SYMBOLS));
        RewardCalculatorWinCombinationRecordDTO record = groupMap.get(SAME_SYMBOLS);

        assertEquals(COMBO_WIN1, record.getName());
        assertEquals(2.0, record.getRule().getRewardMultiplier(), 0.001);
    }

    public Map<String, ConfigWinCombinationDTO> initWinCombos() {
        ConfigWinCombinationDTO winCombo = new ConfigWinCombinationDTO();
        winCombo.setWhen(SAME_SYMBOLS);
        winCombo.setCount(3);
        winCombo.setRewardMultiplier(2.0);
        winCombo.setGroup(SAME_SYMBOLS);

        Map<String, ConfigWinCombinationDTO> winCombos = new HashMap<>();
        winCombos.put(COMBO_WIN1, winCombo);

        return winCombos;
    }
}
