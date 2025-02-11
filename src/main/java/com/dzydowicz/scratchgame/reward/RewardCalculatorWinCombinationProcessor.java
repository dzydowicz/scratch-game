package com.dzydowicz.scratchgame.reward;

import com.dzydowicz.scratchgame.config.dto.ConfigWinCombinationDTO;
import com.dzydowicz.scratchgame.dto.Cell;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

class RewardCalculatorWinCombinationProcessor {

    private static final String SAME_SYMBOLS_WIN_COMBO = "same_symbols";
    private static final String LINEAR_SYMBOLS_WIN_COMBO = "linear_symbols";

    /**
     * @return Map [key: winning symbol, Value: Map (key: win combination group, value: winning combination record)]
     */
    public Map<String, Map<String, RewardCalculatorWinCombinationRecordDTO>> process(Map<String, ConfigWinCombinationDTO> winCombinationsConfig,
                                                                                     Map<String, Integer> symbolCounts,
                                                                                     Cell[][] matrix, int rows, int cols) {
        Map<String, Map<String, RewardCalculatorWinCombinationRecordDTO>> winComboRecords = Maps.newHashMap();

        if (winCombinationsConfig != null) {
            processSameSymbols(winCombinationsConfig, symbolCounts, winComboRecords);
            processLinearSymbols(winCombinationsConfig, matrix, rows, cols, winComboRecords);
        }

        return winComboRecords;
    }

    private void processSameSymbols(Map<String, ConfigWinCombinationDTO> winCombinationsConfig,
                                    Map<String, Integer> symbolCounts,
                                    Map<String, Map<String, RewardCalculatorWinCombinationRecordDTO>> winComboRecords) {
        for (Map.Entry<String, ConfigWinCombinationDTO> entry : winCombinationsConfig.entrySet()) {
            String winComboName = entry.getKey();
            ConfigWinCombinationDTO winCombo = entry.getValue();

            if (SAME_SYMBOLS_WIN_COMBO.equalsIgnoreCase(winCombo.getWhen()) && winCombo.getCount() != null) {
                for (Map.Entry<String, Integer> symEntry : symbolCounts.entrySet()) {
                    String symbol = symEntry.getKey();
                    int count = symEntry.getValue();

                    if (count >= winCombo.getCount()) {
                        updateWinningRecord(winComboRecords, winComboName, winCombo, symbol);
                    }
                }
            }
        }
    }

    private void processLinearSymbols(Map<String, ConfigWinCombinationDTO> winCombinationsConfig,
                                      Cell[][] matrix, int rows, int cols,
                                      Map<String, Map<String, RewardCalculatorWinCombinationRecordDTO>> winComboRecords) {
        for (Map.Entry<String, ConfigWinCombinationDTO> entry : winCombinationsConfig.entrySet()) {
            String winComboName = entry.getKey();
            ConfigWinCombinationDTO winCombo = entry.getValue();

            if (LINEAR_SYMBOLS_WIN_COMBO.equalsIgnoreCase(winCombo.getWhen()) && winCombo.getCoveredAreas() != null) {
                for (List<String> area : winCombo.getCoveredAreas()) {
                    String candidateSymbol = null;
                    boolean valid = true;

                    for (String pos : area) {
                        String[] parts = pos.split(":");

                        Integer currentRow = parts.length == 2 ? Integer.valueOf(parts[0]) : null;
                        Integer currentCol = parts.length == 2 ? Integer.valueOf(parts[1]) : null;

                        if (currentRow == null || currentCol == null) {
                            valid = false;
                            break;
                        }

                        if (currentRow < 0 || currentRow >= rows || currentCol < 0 || currentCol >= cols) {
                            valid = false;
                            break;
                        }

                        Cell cell = matrix[currentRow][currentCol];
                        if (cell.isBonus()) {
                            valid = false;
                            break;
                        }

                        if (candidateSymbol == null) {
                            candidateSymbol = cell.getSymbol();
                        } else if (!candidateSymbol.equals(cell.getSymbol())) {
                            valid = false;
                            break;
                        }
                    }

                    if (valid && candidateSymbol != null) {
                        updateWinningRecord(winComboRecords, winComboName, winCombo, candidateSymbol);
                    }
                }
            }
        }
    }

    private void updateWinningRecord(Map<String, Map<String, RewardCalculatorWinCombinationRecordDTO>> winComboRecords,
                                     String winComboName, ConfigWinCombinationDTO winCombo, String candidateSymbol) {
        Map<String, RewardCalculatorWinCombinationRecordDTO> rec = winComboRecords.computeIfAbsent(candidateSymbol, k -> Maps.newHashMap());
        RewardCalculatorWinCombinationRecordDTO existing = rec.get(winCombo.getGroup());

        if (existing == null || winCombo.getRewardMultiplier() > existing.getRule().getRewardMultiplier()) {
            rec.put(winCombo.getGroup(), new RewardCalculatorWinCombinationRecordDTO(winComboName, winCombo));
        }
    }
}
