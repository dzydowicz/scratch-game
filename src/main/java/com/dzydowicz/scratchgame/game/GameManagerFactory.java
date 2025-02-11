package com.dzydowicz.scratchgame.game;

import com.dzydowicz.scratchgame.config.dto.ConfigDTO;
import com.dzydowicz.scratchgame.matrix.IMatrixGenerator;
import com.dzydowicz.scratchgame.matrix.MatrixGeneratorFactory;
import com.dzydowicz.scratchgame.reward.IRewardCalculator;
import com.dzydowicz.scratchgame.reward.RewardCalculatorFactory;

public class GameManagerFactory {
    public static IGameManager create(ConfigDTO config, int betAmount) {
        IMatrixGenerator matrixGenerator = MatrixGeneratorFactory.create();
        IRewardCalculator rewardCalculator = RewardCalculatorFactory.create();

        return new GameManager(config, betAmount, matrixGenerator, rewardCalculator);
    }
}
