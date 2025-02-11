package com.dzydowicz.scratchgame.matrix;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Random;

@RequiredArgsConstructor
class WeightedRandomSelector {
    private final Random random;

    public String selectWeighted(Map<String, Integer> weightMap) {
        int total = 0;
        for (int weight : weightMap.values()) {
            total += weight;
        }

        int rand = random.nextInt(total) + 1;
        int cumulative = 0;

        for (Map.Entry<String, Integer> entry : weightMap.entrySet()) {
            cumulative += entry.getValue();

            if (rand <= cumulative) {
                return entry.getKey();
            }
        }

        return weightMap.keySet().iterator().next();
    }
}
