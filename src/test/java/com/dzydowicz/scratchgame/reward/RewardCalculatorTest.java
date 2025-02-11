package com.dzydowicz.scratchgame.reward;

import com.dzydowicz.scratchgame.config.dto.ConfigDTO;
import com.dzydowicz.scratchgame.config.dto.ConfigProbabilitiesDTO;
import com.dzydowicz.scratchgame.config.dto.ConfigSymbolDTO;
import com.dzydowicz.scratchgame.config.dto.ConfigWinCombinationDTO;
import com.dzydowicz.scratchgame.dto.Cell;
import com.dzydowicz.scratchgame.dto.Result;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RewardCalculatorTest {

    private final IRewardCalculator rewardCalculator = RewardCalculatorFactory.create();

    @Test
    public void shouldCalculateRewardCorrectly() {
        ConfigDTO config = createTestConfig();
        Cell[][] board = createTestBoard();
        int betAmount = 100;

        Result result = rewardCalculator.calculateReward(config, board, betAmount, 3, 3);

        assertEquals(10000, result.getReward());
        assertTrue(result.getAppliedWinningCombinations().containsKey("A"));
        assertTrue(result.getAppliedWinningCombinations().get("A").contains("win1"));
        assertEquals("BONUS", result.getAppliedBonusSymbol());
    }

    private ConfigDTO createTestConfig() {
        ConfigDTO config = ConfigDTO.builder()
                .rows(3)
                .columns(3)
                .build();

        setSymbols(config);
        setWinCombos(config);

        ConfigProbabilitiesDTO probabilities = new ConfigProbabilitiesDTO();
        config.setProbabilities(probabilities);

        return config;
    }

    private void setSymbols(ConfigDTO config) {
        Map<String, ConfigSymbolDTO> symbols = new HashMap<>();

        ConfigSymbolDTO symbolA = new ConfigSymbolDTO();
        symbolA.setRewardMultiplier(5.0);
        symbols.put("A", symbolA);

        ConfigSymbolDTO bonusSymbol = new ConfigSymbolDTO();
        bonusSymbol.setImpact("multiply_reward");
        bonusSymbol.setRewardMultiplier(10.0);
        symbols.put("BONUS", bonusSymbol);

        config.setSymbols(symbols);
    }

    private void setWinCombos(ConfigDTO config) {
        Map<String, ConfigWinCombinationDTO> winCombos = new HashMap<>();
        ConfigWinCombinationDTO winCombo = new ConfigWinCombinationDTO();
        winCombo.setWhen("same_symbols");
        winCombo.setCount(3);
        winCombo.setRewardMultiplier(2.0);
        winCombo.setGroup("same");
        winCombos.put("win1", winCombo);

        config.setWinCombinations(winCombos);
    }

    private Cell[][] createTestBoard() {
        int size = 3;
        Cell[][] board = new Cell[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Cell("A", false);
            }
        }

        board[1][1] = new Cell("BONUS", true);
        return board;
    }
}
