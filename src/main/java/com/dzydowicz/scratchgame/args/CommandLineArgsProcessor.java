package com.dzydowicz.scratchgame.args;

import com.dzydowicz.scratchgame.args.dto.InputArgsDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommandLineArgsProcessor {
    private final static String CONFIG_ARG = "--config";
    private final static String BETTING_AMOUNT_ARG = "--betting-amount";

    public static InputArgsDTO process(String[] args) {
        String configFilePath = null;
        int betAmount = 0;

        for (int i = 0; i < args.length; i++) {
            if (CONFIG_ARG.equals(args[i]) && i + 1 < args.length) {
                configFilePath = args[++i];
            } else if (BETTING_AMOUNT_ARG.equals(args[i]) && i + 1 < args.length) {
                try {
                    betAmount = Integer.parseInt(args[++i]);
                } catch (NumberFormatException e) {
                    log.error("Invalid betting amount provided.");
                    System.exit(1);
                }
            }
        }

        if (configFilePath == null) {
            log.error("Config file must be provided.");
            System.exit(1);
        }

        if (betAmount <= 0) {
            log.error("Betting amount must be greater than zero.");
            System.exit(1);
        }

        return new InputArgsDTO(configFilePath, betAmount);
    }

}
