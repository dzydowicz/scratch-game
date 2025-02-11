package com.dzydowicz.scratchgame.reward;

public class RewardCalculatorFactory {
    public static IRewardCalculator create() {
        RewardCalculatorWinCombinationProcessor winCombinationProcessor = new RewardCalculatorWinCombinationProcessor();
        RewardCalculatorBonusProcessor bonusProcessor = new RewardCalculatorBonusProcessor();
        RewardCalculatorTotalRewardCalculator totalRewardCalculator= new RewardCalculatorTotalRewardCalculator();

        return new RewardCalculator(winCombinationProcessor, bonusProcessor, totalRewardCalculator);
    }
}
