package com.dzydowicz.scratchgame;

import com.dzydowicz.scratchgame.args.CommandLineArgsProcessor;
import com.dzydowicz.scratchgame.args.dto.InputArgsDTO;
import com.dzydowicz.scratchgame.config.ConfigLoader;
import com.dzydowicz.scratchgame.config.dto.ConfigDTO;
import com.dzydowicz.scratchgame.dto.Result;
import com.dzydowicz.scratchgame.game.GameManagerFactory;
import com.dzydowicz.scratchgame.game.IGameManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ScratchGameApp {

    public static void main(String[] args) {
        InputArgsDTO inputArgs = CommandLineArgsProcessor.process(args);
        ConfigDTO config = loadConfig(inputArgs.getConfigFilePath());

        IGameManager gameManager = GameManagerFactory.create(config, inputArgs.getBetAmount());
        Result result = gameManager.playGame();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        log.error(gson.toJson(result));
    }

    private static ConfigDTO loadConfig(String configFilePath) {
        ConfigDTO config = null;

        try {
            config = ConfigLoader.load(configFilePath);
        } catch (IOException e) {
            log.error("Error loading configuration: " + e.getMessage());
            System.exit(1);
        }

        return config;
    }
}