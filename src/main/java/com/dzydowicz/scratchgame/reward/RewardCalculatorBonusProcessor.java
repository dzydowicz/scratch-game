package com.dzydowicz.scratchgame.reward;

import com.dzydowicz.scratchgame.config.dto.ConfigDTO;
import com.dzydowicz.scratchgame.config.dto.ConfigSymbolDTO;
import com.dzydowicz.scratchgame.dto.Cell;

class RewardCalculatorBonusProcessor {
    private static final String MISS = "miss";

    public String selectBonusSymbol(ConfigDTO config, Cell[][] matrix, int rows, int cols, double totalReward) {
        String result = null;

        if (totalReward > 0) {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    Cell cell = matrix[r][c];

                    if (cell.isBonus()) {
                        ConfigSymbolDTO bonusCfg = config.getSymbols().get(cell.getSymbol());

                        if (bonusCfg != null && bonusCfg.getImpact() != null &&
                                !bonusCfg.getImpact().equalsIgnoreCase(MISS)) {
                            result = cell.getSymbol();
                        }
                    }
                }
            }
        }

        return result;
    }
}
