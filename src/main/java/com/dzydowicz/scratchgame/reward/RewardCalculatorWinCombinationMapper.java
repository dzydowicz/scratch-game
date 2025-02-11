package com.dzydowicz.scratchgame.reward;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

class RewardCalculatorWinCombinationMapper {
    public static Map<String, List<String>> toAppliedWinCombinationNames(Map<String, Map<String, RewardCalculatorWinCombinationRecordDTO>> winComboRecords) {
        Map<String, List<String>> appliedWinCombos = Maps.newHashMap();

        for (Map.Entry<String, Map<String, RewardCalculatorWinCombinationRecordDTO>> entry : winComboRecords.entrySet()) {
            String symbol = entry.getKey();
            List<String> winNames = Lists.newArrayList();

            for (RewardCalculatorWinCombinationRecordDTO record : entry.getValue().values()) {
                winNames.add(record.getName());
            }

            appliedWinCombos.put(symbol, winNames);
        }
        return appliedWinCombos;
    }
}
