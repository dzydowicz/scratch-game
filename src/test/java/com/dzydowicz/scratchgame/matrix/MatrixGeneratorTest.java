package com.dzydowicz.scratchgame.matrix;

import com.dzydowicz.scratchgame.config.dto.ConfigBonusProbabilityDTO;
import com.dzydowicz.scratchgame.config.dto.ConfigDTO;
import com.dzydowicz.scratchgame.config.dto.ConfigProbabilitiesDTO;
import com.dzydowicz.scratchgame.config.dto.ConfigStandardProbabilityDTO;
import com.dzydowicz.scratchgame.dto.Cell;
import com.dzydowicz.scratchgame.matrix.exceptions.MatrixGenerationException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatrixGeneratorTest {

    private static final String DEFAULT_SYMBOL = "DEFAULT_SYMBOL";
    private static final String SPECIFIC_SYMBOL = "SPECIFIC_SYMBOL";
    private static final String BONUS_SYMBOL = "BONUS_SYMBOL";
    
    @Mock
    private Random random;

    @Mock
    private WeightedRandomSelector weightedSelector;

    private MatrixGenerator matrixGenerator;

    @BeforeEach
    void setUp() {
        matrixGenerator = new MatrixGenerator(random, weightedSelector);
    }

    @Test
    void testGenerateMatrix_withCellSpecificProbabilityAndBonusCell() {
        int rows = 2;
        int cols = 2;
        
        ConfigDTO config = createConfigWithStandardAndBonus(rows, cols, true);

        when(weightedSelector.selectWeighted(getDefaultSymbolWeights())).thenReturn(DEFAULT_SYMBOL);
        when(weightedSelector.selectWeighted(getSpecificSymbolWeights())).thenReturn(SPECIFIC_SYMBOL);
        when(weightedSelector.selectWeighted(getBonusSymbolWeights())).thenReturn(BONUS_SYMBOL);

        when(random.nextDouble()).thenReturn(0.99, 0.10, 0.50, 0.50);

        Cell[][] resultMatrix = matrixGenerator.generateMatrix(config, rows, cols);

        assertEquals(2, resultMatrix.length);
        assertEquals(2, resultMatrix[0].length);

        // (0,0): Uses default probability → DEFAULT_SYMBOL, no bonus.
        assertEquals(DEFAULT_SYMBOL, resultMatrix[0][0].getSymbol());
        assertFalse(resultMatrix[0][0].isBonus());

        // (0,1): Uses specific probability, but bonus is triggered (random < 0.20) → BONUS_SYMBOL.
        assertEquals(BONUS_SYMBOL, resultMatrix[0][1].getSymbol());
        assertTrue(resultMatrix[0][1].isBonus());

        // (1,0): Uses default probability → DEFAULT_SYMBOL, no bonus.
        assertEquals(DEFAULT_SYMBOL, resultMatrix[1][0].getSymbol());
        assertFalse(resultMatrix[1][0].isBonus());

        // (1,1): Uses default probability → DEFAULT_SYMBOL, no bonus.
        assertEquals(DEFAULT_SYMBOL, resultMatrix[1][1].getSymbol());
        assertFalse(resultMatrix[1][1].isBonus());
    }

    @Test
    void testGenerateMatrix_noStandardSymbolsConfigured_throwsException() {
        ConfigDTO config = createConfigWithNoStandardSymbols();

        assertThrows(MatrixGenerationException.class,
                () -> matrixGenerator.generateMatrix(config, 2, 2),
                "Expected a RuntimeException because no default Standard Probability is found");
    }

    @Test
    void testGenerateMatrix_noBonusDueToRandom() {
        int rows = 1;
        int cols = 1;
        
        ConfigDTO config = createConfigWithStandardAndBonus(rows, cols, false);

        when(weightedSelector.selectWeighted(getDefaultSymbolWeights())).thenReturn(DEFAULT_SYMBOL);
        when(random.nextDouble()).thenReturn(0.25);

        Cell[][] resultMatrix = matrixGenerator.generateMatrix(config, rows, cols);

        assertEquals(1, resultMatrix.length);
        assertEquals(1, resultMatrix[0].length);

        Cell cell = resultMatrix[0][0];
        assertEquals(DEFAULT_SYMBOL, cell.getSymbol());
        assertFalse(cell.isBonus(), "Expected no bonus because random >= 0.20");
    }
    
    private ConfigDTO createConfigWithStandardAndBonus(int rows, int cols, boolean includeSpecific) {
        ConfigDTO config = new ConfigDTO();
        config.setRows(rows);
        config.setColumns(cols);

        ConfigProbabilitiesDTO probabilitiesDTO = new ConfigProbabilitiesDTO();
        
        List<ConfigStandardProbabilityDTO> standardList = Lists.newArrayList();
        standardList.add(createDefaultStandardProbabilityDTO());
        
        if (includeSpecific) {
            standardList.add(createSpecificStandardProbabilityDTO());
        }
        
        probabilitiesDTO.setStandardSymbols(standardList);
        probabilitiesDTO.setBonusSymbols(createBonusProbabilityDTO());
        config.setProbabilities(probabilitiesDTO);

        return config;
    }

    private ConfigDTO createConfigWithNoStandardSymbols() {
        ConfigProbabilitiesDTO probabilitiesDTO = new ConfigProbabilitiesDTO();
        probabilitiesDTO.setStandardSymbols(Lists.newArrayList());

        return ConfigDTO.builder()
                .probabilities(probabilitiesDTO)
                .build();
    }

    private ConfigStandardProbabilityDTO createDefaultStandardProbabilityDTO() {
        ConfigStandardProbabilityDTO dto = new ConfigStandardProbabilityDTO();
        dto.setRow(0);
        dto.setColumn(0);
        dto.setSymbols(getDefaultSymbolWeights());
        
        return dto;
    }

    private ConfigStandardProbabilityDTO createSpecificStandardProbabilityDTO() {
        ConfigStandardProbabilityDTO dto = new ConfigStandardProbabilityDTO();
        dto.setRow(0);
        dto.setColumn(1);
        dto.setSymbols(getSpecificSymbolWeights());
        
        return dto;
    }

    private ConfigBonusProbabilityDTO createBonusProbabilityDTO() {
        ConfigBonusProbabilityDTO dto = new ConfigBonusProbabilityDTO();
        dto.setSymbols(getBonusSymbolWeights());
        return dto;
    }

    private Map<String, Integer> getDefaultSymbolWeights() {
        Map<String, Integer> map = Maps.newHashMap();
        map.put(DEFAULT_SYMBOL, 100);
        return map;
    }

    private Map<String, Integer> getSpecificSymbolWeights() {
        Map<String, Integer> map = Maps.newHashMap();
        map.put(SPECIFIC_SYMBOL, 100);
        return map;
    }

    private Map<String, Integer> getBonusSymbolWeights() {
        Map<String, Integer> map = Maps.newHashMap();
        map.put(BONUS_SYMBOL, 50);
        return map;
    }
}
