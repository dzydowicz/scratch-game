package com.dzydowicz.scratchgame.reward;

import com.dzydowicz.scratchgame.dto.Cell;
import com.google.common.collect.Maps;
import java.util.Map;

class RewardCalculatorSymbolCounter {
    public static Map<String, Integer> count(Cell[][] matrix, int rows, int cols) {
        Map<String, Integer> counts = Maps.newHashMap();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = matrix[r][c];

                if (!cell.isBonus()) {
                    counts.put(cell.getSymbol(), counts.getOrDefault(cell.getSymbol(), 0) + 1);
                }
            }
        }

        return counts;
    }
}
