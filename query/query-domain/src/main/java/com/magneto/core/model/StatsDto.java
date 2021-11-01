package com.magneto.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StatsDto {
    protected int countMutantDna;
    protected int countHumanDna;
    protected double ratio;
}