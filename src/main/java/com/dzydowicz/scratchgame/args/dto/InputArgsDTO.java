package com.dzydowicz.scratchgame.args.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Getter
public class InputArgsDTO {
    private final String configFilePath;
    private final int betAmount;
}
