package com.dzydowicz.scratchgame.matrix;

import com.dzydowicz.scratchgame.config.dto.ConfigDTO;
import com.dzydowicz.scratchgame.dto.Cell;

public interface IMatrixGenerator {
    Cell[][] generateMatrix(ConfigDTO config, int rows, int cols);
}
