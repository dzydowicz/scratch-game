package com.dzydowicz.scratchgame.config;

import com.dzydowicz.scratchgame.config.dto.ConfigDTO;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class ConfigLoader {
    public static ConfigDTO load(String configFilePath) throws IOException {
        try (Reader reader = new FileReader(configFilePath)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, ConfigDTO.class);
        }
    }
}
