package com.dzydowicz.scratchgame.matrix;

import com.dzydowicz.scratchgame.dto.Cell;
import com.dzydowicz.scratchgame.config.dto.ConfigDTO;
import com.dzydowicz.scratchgame.config.dto.ConfigStandardProbabilityDTO;
import com.dzydowicz.scratchgame.matrix.exceptions.MatrixGenerationException;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Random;

@RequiredArgsConstructor
class MatrixGenerator implements IMatrixGenerator {
    private static final double BONUS_CELL_CHANCE = 0.20;

    private final Random random;
    private final WeightedRandomSelector weightedSelector;

    @Override
    public Cell[][] generateMatrix(ConfigDTO config, int rows, int cols) {
        Cell[][] matrix = new Cell[rows][cols];
        Map<String, ConfigStandardProbabilityDTO> cellProbabilityMap = buildCellProbabilityMap(config);

        ConfigStandardProbabilityDTO defaultStandardProb = getDefaultStandardProbability(config);
        if (defaultStandardProb == null) {
            throw new MatrixGenerationException("No standard symbols probability configuration found!");
        }

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                String key = r + ":" + c;
                ConfigStandardProbabilityDTO sp = cellProbabilityMap.getOrDefault(key, defaultStandardProb);

                matrix[r][c] = generateCell(config, sp);
            }
        }

        return matrix;
    }

    private Map<String, ConfigStandardProbabilityDTO> buildCellProbabilityMap(ConfigDTO config) {
        Map<String, ConfigStandardProbabilityDTO> cellMap = Maps.newHashMap();

        boolean hasStandardSymbols = config.getProbabilities() != null
                && config.getProbabilities().getStandardSymbols() != null;

        if (hasStandardSymbols) {
            for (ConfigStandardProbabilityDTO sp : config.getProbabilities().getStandardSymbols()) {
                String key = sp.getRow() + ":" + sp.getColumn();
                cellMap.put(key, sp);
            }
        }

        return cellMap;
    }

    private ConfigStandardProbabilityDTO getDefaultStandardProbability(ConfigDTO config) {
        ConfigStandardProbabilityDTO result = null;

        boolean hasStandardSymbols = config.getProbabilities() != null
                && config.getProbabilities().getStandardSymbols() != null
                && !config.getProbabilities().getStandardSymbols().isEmpty();

        if (hasStandardSymbols) {
            result = config.getProbabilities().getStandardSymbols().getFirst();
        }

        return result;
    }

    private Cell generateCell(ConfigDTO config, ConfigStandardProbabilityDTO sp) {
        String symbol = weightedSelector.selectWeighted(sp.getSymbols());
        Cell cell = new Cell(symbol, false);

        boolean shouldOverrideWithBonus = random.nextDouble() < BONUS_CELL_CHANCE
                && config.getProbabilities() != null
                && config.getProbabilities().getBonusSymbols() != null
                && config.getProbabilities().getBonusSymbols().getSymbols() != null;

        if (shouldOverrideWithBonus) {
            String bonusSymbol = selectBonusSymbol(config);

            if (bonusSymbol != null) {
                cell.setSymbol(bonusSymbol);
                cell.setBonus(true);
            }
        }
        return cell;
    }

    private String selectBonusSymbol(ConfigDTO config) {
        return weightedSelector.selectWeighted(config.getProbabilities().getBonusSymbols().getSymbols());
    }
}
