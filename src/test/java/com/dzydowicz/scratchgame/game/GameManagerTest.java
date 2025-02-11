package com.dzydowicz.scratchgame.game;

import com.dzydowicz.scratchgame.config.dto.ConfigDTO;
import com.dzydowicz.scratchgame.dto.Cell;
import com.dzydowicz.scratchgame.dto.Result;
import com.dzydowicz.scratchgame.matrix.IMatrixGenerator;
import com.dzydowicz.scratchgame.reward.IRewardCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameManagerTest {

    private static final int BET_AMOUNT = 10;

    @Mock
    private ConfigDTO config;

    @Mock
    private IMatrixGenerator matrixGenerator;

    @Mock
    private IRewardCalculator rewardCalculator;

    private GameManager gameManager;

    @BeforeEach
    void setUp() {
        gameManager = new GameManager(config, BET_AMOUNT, matrixGenerator, rewardCalculator);
    }

    @Test
    void testPlayGame_withDefaultRowsAndColumns() {
        when(config.getRows()).thenReturn(null);
        when(config.getColumns()).thenReturn(null);

        Cell[][] mockMatrix = {
                { new Cell("A", false), new Cell("B", false), new Cell("C", false) },
                { new Cell("D", false), new Cell("E", false), new Cell("F", false) },
                { new Cell("G", false), new Cell("H", false), new Cell("I", false) }
        };
        when(matrixGenerator.generateMatrix(config, 3, 3)).thenReturn(mockMatrix);

        Result mockResult = Result.builder().build();
        when(rewardCalculator.calculateReward(config, mockMatrix, BET_AMOUNT, 3, 3)).thenReturn(mockResult);

        Result result = gameManager.playGame();

        verify(matrixGenerator).generateMatrix(config, 3, 3);
        verify(rewardCalculator).calculateReward(config, mockMatrix, BET_AMOUNT, 3, 3);
        assertSame(mockResult, result, "Expected the same Result object returned by the reward calculator.");

        List<List<String>> expectedMatrix = List.of(
                List.of("A", "B", "C"),
                List.of("D", "E", "F"),
                List.of("G", "H", "I")
        );
        assertEquals(expectedMatrix, result.getMatrix(), "The matrix in the result should match the converted cell array.");
    }

    @Test
    void testPlayGame_withSpecificRowsAndColumns() {
        when(config.getRows()).thenReturn(4);
        when(config.getColumns()).thenReturn(5);

        Cell[][] mockMatrix = new Cell[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                mockMatrix[i][j] = new Cell("X", false);
            }
        }
        when(matrixGenerator.generateMatrix(config, 4, 5)).thenReturn(mockMatrix);

        Result mockResult = Result.builder().build();
        when(rewardCalculator.calculateReward(config, mockMatrix, BET_AMOUNT, 4, 5)).thenReturn(mockResult);

        Result result = gameManager.playGame();

        verify(matrixGenerator).generateMatrix(config, 4, 5);
        verify(rewardCalculator).calculateReward(config, mockMatrix, BET_AMOUNT, 4, 5);
        assertSame(mockResult, result, "Expected the same Result object returned by the reward calculator.");

        List<List<String>> expectedMatrix = List.of(
                List.of("X", "X", "X", "X", "X"),
                List.of("X", "X", "X", "X", "X"),
                List.of("X", "X", "X", "X", "X"),
                List.of("X", "X", "X", "X", "X")
        );
        assertEquals(expectedMatrix, result.getMatrix());
    }
}

