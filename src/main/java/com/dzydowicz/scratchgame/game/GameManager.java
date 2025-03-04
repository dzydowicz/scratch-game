package com.dzydowicz.scratchgame.game;

import com.dzydowicz.scratchgame.config.dto.ConfigDTO;
import com.dzydowicz.scratchgame.dto.Cell;
import com.dzydowicz.scratchgame.dto.Result;
import com.dzydowicz.scratchgame.matrix.IMatrixGenerator;
import com.dzydowicz.scratchgame.reward.IRewardCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
class GameManager implements IGameManager {
    private final ConfigDTO config;
    private final int betAmount;
    private final IMatrixGenerator matrixGenerator;
    private final IRewardCalculator rewardCalculator;

    public Result playGame() {
        log.info("Starting game with bet amount {}", betAmount);

        int rows = config.getRows() != null ? config.getRows() : 3;
        int columns = config.getColumns() != null ? config.getColumns() : 3;

        Cell[][] matrix = matrixGenerator.generateMatrix(config, rows, columns);
        log.info("Matrix generated, proceeding to calculate rewards");

        Result result = rewardCalculator.calculateReward(config, matrix, betAmount, rows, columns);
        result.setMatrix(convertMatrixToList(matrix));

        log.info("Calculated rewards, finishing game");
        return result;
    }

    private List<List<String>> convertMatrixToList(Cell[][] matrix) {
        return Arrays.stream(matrix)
                .map(row -> Arrays.stream(row)
                        .map(Cell::getSymbol)
                        .toList())
                .toList();
    }
}
