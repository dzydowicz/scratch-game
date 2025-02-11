package com.dzydowicz.scratchgame.matrix;

import java.util.Random;

public class MatrixGeneratorFactory {
    public static IMatrixGenerator create() {
        Random random = new Random();
        WeightedRandomSelector weightedRandomSelector = new WeightedRandomSelector(random);

        return new MatrixGenerator(random, weightedRandomSelector);
    }
}
