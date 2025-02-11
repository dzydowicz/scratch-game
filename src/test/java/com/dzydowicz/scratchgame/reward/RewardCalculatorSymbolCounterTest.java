package com.dzydowicz.scratchgame.reward;

import com.dzydowicz.scratchgame.dto.Cell;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RewardCalculatorSymbolCounterTest {

    @Test
    void testCountSymbols() {
        Cell[][] matrix = new Cell[][] {
                { new Cell("A", false), new Cell("B", false) },
                { new Cell("A", false), new Cell("A", true) }
        };

        Map<String, Integer> counts = RewardCalculatorSymbolCounter.count(matrix, 2, 2);

        assertEquals(Integer.valueOf(2), counts.get("A"));
        assertEquals(Integer.valueOf(1), counts.get("B"));
    }
}
